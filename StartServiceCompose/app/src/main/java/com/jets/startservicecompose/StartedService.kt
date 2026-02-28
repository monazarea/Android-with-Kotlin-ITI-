package com.jets.startservicecompose

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class StartedService() : Service() {

    var th: Thread? = null

    override fun onCreate() {
        super.onCreate()
        Log.i("TAG", "onCreate")
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("TAG", "onStartCommand begins")
        th = object : Thread() {
            override fun run() {
                try {
                    val endTime = System.currentTimeMillis() + 10_000

                    while (Thread.currentThread().isInterrupted.not() && System.currentTimeMillis() < endTime) {

                        Thread.sleep(1000)
                        Log.i("TAG", "Service running...")
                    }

                    Log.i("TAG", "Service finished!")

                } catch (e: InterruptedException) {
                    Log.i("TAG", "Thread interrupted")
                }
            }
        }
        th?.start()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("TAG", "onDestroy")
        th?.interrupt()
    }

    override fun onBind(intent: Intent?): IBinder? {
        // TODO: Return the communication channel to the service.
        return null
    }
}