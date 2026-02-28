package com.jets.startservicecompose

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService


class MyJobIntentService() : JobIntentService() {

    override fun onCreate() {
        super.onCreate()
        Log.i("TAG", "onCreate: ")
    }

    companion object {
        private const val JOB_ID = 10

        fun enqueueWork(context: Context, work: Intent) {
            enqueueWork(context, MyJobIntentService::class.java, JOB_ID, work)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(11, createNotification(this).build())
        return START_NOT_STICKY
    }

    override fun onHandleWork(intent: Intent) {
        // Background work
        /*Log.i("TAG", "onHandleWork begins")
        val endTime = System.currentTimeMillis() + 70 * 1000
        while (System.currentTimeMillis() < endTime) {
            synchronized(this) {
                Thread.sleep(endTime - System.currentTimeMillis())
                Log.i("TAG", "onHandleWork Ended!")
            }
        }*/
        Log.i("TAG", "Work started")
        Thread.sleep(10_000)
        Log.i("TAG", "Work finished")
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}