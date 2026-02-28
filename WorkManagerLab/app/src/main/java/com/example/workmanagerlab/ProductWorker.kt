package com.example.workmanagerlab

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.google.gson.Gson

class ProductWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val retrofit = RetrofitHelper.retrofitInstance

        val apiService = retrofit.create(ApiService::class.java)
        val response = apiService.getProducts()
        return try {
            if (response.isSuccessful && response.body() != null) {
                val productsList = response.body()!!.products
                val productsJson = Gson().toJson(productsList)
                val outputData = workDataOf("PRODUCTS_JSON" to productsJson)
                Result.success(outputData)
            } else {
                Result.failure()
            }
        } catch (e: Exception) {
            Result.failure()
        }
    }
}