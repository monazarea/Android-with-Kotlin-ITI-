package com.example.androidlab2

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.number.Precision.increment
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import com.example.androidlab2.ui.theme.AndroidLab2Theme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

class MainActivity : ComponentActivity() {

    companion object {
        private const val SMS_SCHEME = "smsto:"
        private const val SMS_BODY_KEY = "sms_body"
        private const val REQUEST_LOCATION_CODE = 2026
    }

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient // last know location & updated location
    private lateinit var locationState: MutableState<Location>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidLab2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    locationState = remember { mutableStateOf(Location("")) }
                    val location = locationState.value
                    val addressText = if (location.latitude != 0.0) {
                        getAddressFromLocation(location.latitude, location.longitude)
                    } else {
                        "Waiting for location..."
                    }
                    LocationDetails(
                        location = locationState.value,
                        addressText = addressText,
                        modifier = Modifier.padding(innerPadding),
                        onSendSMSClick = { message ->
                            sendSMS("01023486493", message)
                        },
                        onOpenMapClick = {
                            openMap(location.latitude, location.longitude)
                        }
                    )
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (checkPermission()) {
            if (isLocationEnabled()) {
                getFreshLocation()
            } else {
                enableLocationService()
            }
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                REQUEST_LOCATION_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String?>,
        grantResults: IntArray,
        deviceId: Int
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId)
        when (requestCode) {
            REQUEST_LOCATION_CODE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED || grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    if (isLocationEnabled()) {
                        getFreshLocation()
                    } else {
                        enableLocationService()
                    }
                }
            }
        }
    }

    fun checkPermission() =
        checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

    fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    fun enableLocationService() {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivity(intent)
    }

    @SuppressLint("MissingPermission")
    fun getFreshLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationProviderClient.requestLocationUpdates(
            LocationRequest.Builder(0).apply {
                setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            }.build(),
            object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    super.onLocationResult(locationResult)
                    Log.i("asd->", "onLocationResult: ${locationResult.locations}")
                    locationState.value = locationResult.lastLocation ?: Location("")
                }
            },
            Looper.myLooper()
        )

    }

    fun getAddressFromLocation(latitude: Double, longitude: Double): String {
        return try {
            val geocoder = Geocoder(this, java.util.Locale.getDefault())
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            val address = addresses?.firstOrNull()
            if (address != null) {
                val street = address.thoroughfare ?: "Unknown Street"
                val city = address.adminArea ?: ""
                val country = address.countryName ?: ""
                "Street: $street\nCity: $city\nCountry: $country"
            } else {
                "Address not found"
            }
        } catch (e: Exception) {
            "Error: ${e.message}"
        }

    }

    fun sendSMS(phoneNumber: String, message: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = android.net.Uri.parse(SMS_SCHEME + phoneNumber)
            putExtra(SMS_BODY_KEY, message)
        }
        startActivity(intent)
    }

    fun openMap(latitude: Double, longitude: Double) {
        val uri =
            android.net.Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude(My Location)")
        val intent = Intent(Intent.ACTION_VIEW, uri)

        intent.setPackage("com.google.android.apps.maps")
        startActivity(intent)
    }

}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LocationDetailsPreview(){
    AndroidLab2Theme{
        LocationDetails(
            location = Location("preview").apply {
                latitude = 30.8
                longitude = 30.99
            },
            addressText = "Cairo, Egypt",
            modifier = Modifier,
            onSendSMSClick = {},
            onOpenMapClick = {}
        )
    }
}

@Composable
fun LocationDetails(location: Location,addressText:String, modifier: Modifier,onSendSMSClick:(String)-> Unit,onOpenMapClick:()->Unit){
    Column(
        modifier = modifier.fillMaxSize().padding(top = 50.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = modifier.fillMaxWidth().padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = "Longitude:",
                fontWeight = FontWeight.Bold
            )
            Text("${location.longitude}")
        }
        Spacer(Modifier.height(24.dp))
        Row(
            modifier = modifier.fillMaxWidth().padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = "Latitude:",
                fontWeight = FontWeight.Bold
            )
            Text("${location.latitude}")
        }
        Spacer(Modifier.height(24.dp))
        Row(
            modifier = modifier.fillMaxWidth().padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top
        ) {
            Text(text = addressText)
        }

        Button(
            onClick = {
                val fullMessage = "الحقوني يا ناس انا مخطوفاااا \n عنواني اهه $addressText"
                onSendSMSClick(fullMessage)
            },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
        ) {
            Text(text = "Open SMS")
        }

        Button(
            onClick = {
                onOpenMapClick()
            },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp))
        {
            Text(text ="Open Map" )
        }


    }
}




@Composable
fun Counter(modifier: Modifier= Modifier){
    var counter by rememberSaveable {mutableIntStateOf(0)}
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Counter = $counter"
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                println("asd --> before counter = $counter")
                counter++
                println("asd --> after counter = $counter")
            }
        ) {
            Text(text = stringResource(R.string.increment))
        }

    }
}


