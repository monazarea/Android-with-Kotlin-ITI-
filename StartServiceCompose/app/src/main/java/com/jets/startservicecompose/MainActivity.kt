package com.jets.startservicecompose

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

private fun isNewerOrEqualToOreo() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

class MainActivity : ComponentActivity() {

    companion object {
        private const val REQUEST_NOTIFICATION_CODE = 100
    }

    private val notificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isPermissionGranted ->
            if (isPermissionGranted) {
                Toast.makeText(this, "Granted!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Denied!", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            StartServiceScreen(this)

            StartIntentServiceScreen(this)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            /*requestPermissions(
                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                REQUEST_NOTIFICATION_CODE
            )*/
            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}

// 1. Start Service
@Composable
fun StartServiceScreen(context: Context) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(onClick = {
            val intent = Intent(context, StartedService::class.java)
            context.startService(intent)
        }) {
            Text("Start Service")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val intent = Intent(context, StartedService::class.java)
            context.stopService(intent)
        }) {
            Text("Stop Service")
        }
    }
}

// 3. Intent Service
@Composable
fun StartIntentServiceScreen(context: Context) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                startMyService(context)
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Start Service")
        }
        Button(
            onClick = { stopMyService(context) },
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Stop Service")
        }
    }
}

fun startMyService(context: Context) {
    val intent = Intent(context, MyIntentService::class.java)
    if (isNewerOrEqualToOreo()) {
        context.startForegroundService(intent)
    } else {
        context.startService(intent)
    }

//    val intent = Intent(context, MyJobIntentService::class.java)
//    MyJobIntentService.enqueueWork(context, intent)
}

fun stopMyService(context: Context) {
    val intent = Intent(context, MyIntentService::class.java)
    if (isNewerOrEqualToOreo()) {
        intent.putExtra("Type", "Foreground")
        context.startService(intent)
    } else {
        context.stopService(intent)
    }
}
