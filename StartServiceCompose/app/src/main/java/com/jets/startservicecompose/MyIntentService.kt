package com.jets.startservicecompose

import android.app.IntentService
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi


class MyIntentService() : IntentService("MyIntentService") {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("TAG", "onStartCommand: ")
        if (intent?.getStringExtra("Type") == "Foreground") {
            stopForeground(STOP_FOREGROUND_REMOVE)
            stopSelf()
        } else {
            startForeground(10, createNotification(this).build())
        }
        return START_NOT_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        Log.i("TAG", "onCreate: ")
    }

    override fun onHandleIntent(intent: Intent?) {
        Log.i("TAG", "IntentService onHandleIntent Beginning")
        val endTime = System.currentTimeMillis() + 20 * 1000
        while (System.currentTimeMillis() < endTime) {
            synchronized(this) {
                Thread.sleep(endTime - System.currentTimeMillis())
            }
        }
        Log.i("TAG", "IntentService onHandleIntent End!")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("Service", "IntentService onDestroy")
        Log.i("TAG", "IntentService onHandleIntent End!")
    }
}
