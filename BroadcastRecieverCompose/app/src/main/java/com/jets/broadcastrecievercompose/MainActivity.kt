package com.jets.broadcastrecievercompose

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jets.broadcastrecievercompose.ui.theme.BroadcastRecieverComposeTheme

class MainActivity : ComponentActivity() {
    private lateinit var receiver: MyReceiver
    private val intentFilter = IntentFilter(Intent.ACTION_BATTERY_LOW)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        receiver = MyReceiver()

        setContent {
            BroadcastScreen(context = this)
        }
    }

    override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(receiver, intentFilter, RECEIVER_NOT_EXPORTED)
        } else {
            registerReceiver(receiver, intentFilter)
        }
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(receiver)
    }
}

@Composable
fun BroadcastScreen(context: Context) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            broadcastIntent(context)
        }) {
            Text("Send Broadcast")
        }
    }
}

fun broadcastIntent(context: Context) {
    val intent = Intent().apply {
        action = "com.example.SendBroadCast"
        addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
    }
    context.sendBroadcast(intent)
}

/*
@Composable
fun BroadcastScreen(context: Context) {
    val receiver = remember { MyReceiver() }
    val intentFilter = remember { IntentFilter(Intent.ACTION_BATTERY_LOW) }

    // Register and unregister the receiver properly
    DisposableEffect(context) {
        context.registerReceiver(receiver, intentFilter)

        onDispose {
            context.unregisterReceiver(receiver)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            val intent = Intent().apply {
                action = "com.example.SendBroadCast"
                addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
            }
            context.sendBroadcast(intent)
        }) {
            Text("Send Broadcast")
        }
    }
}
*/
