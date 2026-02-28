package com.jets.broadcastrecievercompose

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

const val TAG = "MyReceiver"
class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver
        // is receiving an Intent broadcast.
        //Log.i(TAG, "== onReceive: com.example.SendBroadCast ==")
        Log.i(TAG, "== onReceive:Battery Loooow ==")
    }
}
