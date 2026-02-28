package com.jets.boundservicecompose

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jets.boundservicecompose.ui.theme.BoundServiceComposeTheme

class MainActivity : ComponentActivity() {
    private var myService by mutableStateOf<BoundService?>(null)
    private var isBound by mutableStateOf(false)

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as BoundService.MyLocalBinder
            myService = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
        }
    }

   /* override fun onStart() {
        super.onStart()
        val intent = Intent(this, BoundService::class.java)
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, BoundService::class.java)
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)

        setContent {
            LocalBoundScreen(myService = myService)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isBound) {
            unbindService(serviceConnection)
        }
    }
}

@Composable
fun LocalBoundScreen(myService: BoundService?) {
    var currentTime by remember { mutableStateOf("Press the button to get time") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = currentTime, fontSize = 20.sp, modifier = Modifier.padding(8.dp))

        Button(onClick = {
            myService?.let {
                currentTime = it.getCurrentTime()
            }
        }) {
            Text("Get Current Time")
        }
    }
}

/*@Composable
fun LocalBoundScreen(context: Context) {
    var myService by remember { mutableStateOf<BoundService?>(null) }
    var isBound by remember { mutableStateOf(false) }
    var currentTime by remember { mutableStateOf("Press the button to get time") }

    val serviceConnection = remember {
        object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                val binder = service as BoundService.MyLocalBinder
                myService = binder.getService()
                isBound = true
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                isBound = false
                myService = null
            }
        }
    }


/*LaunchedEffect(Unit) {
        val intent = Intent(context, BoundService::class.java)
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }*/


    DisposableEffect(context) {
        val intent = Intent(context, BoundService::class.java)
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)

        onDispose {
            if (isBound) {
                context.unbindService(serviceConnection)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = currentTime, fontSize = 20.sp, modifier = Modifier.padding(8.dp))

        Button(onClick = {
            myService?.let {
                currentTime = it.getCurrentTime()
            }
        }) {
            Text("Get Current Time")
        }
    }
}*/

