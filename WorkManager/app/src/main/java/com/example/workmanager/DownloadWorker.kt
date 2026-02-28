package com.example.workmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import okio.IOException

class DownloadWorker(
    private var context: Context,
    private var workParams: WorkerParameters
): Worker (context,workParams){

    override fun doWork(): Result {
        val apiInstance = RetrofitHelper.retrofitInstance.create(ApiService::class.java)
        val response = apiInstance.getImage().execute()
        return if(response.isSuccessful){
            val fos = context.openFileOutput("myImage.png", Context.MODE_PRIVATE)
            try {
                fos.write(response?.body()?.bytes())
                fos.flush()
                Result.success(workDataOf("File Name" to "myImage.png"))
            }
            catch (ex: java.io.IOException){
                fos.close()
                Result.failure(workDataOf("FAILURE_REASON" to ex.message))
            }
        }else{
            Result.failure(workDataOf("FAILURE_REASON" to response.errorBody()))

        }
    }
}