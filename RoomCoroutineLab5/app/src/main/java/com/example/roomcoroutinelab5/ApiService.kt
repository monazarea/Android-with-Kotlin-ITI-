package com.example.roomcoroutinelab5

import retrofit2.http.GET

interface ApiService {
    @GET("products")
    suspend fun getProducts(): ProductResponse 
}