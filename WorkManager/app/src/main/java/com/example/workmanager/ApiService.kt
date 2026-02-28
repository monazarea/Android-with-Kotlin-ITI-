package com.example.workmanager;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit
import retrofit2.http.GET

interface ApiService {

    @GET("czNmcy1wcml2YXRlL3Jhd3BpeGVsX2ltYWdlcy93ZWJzaXRlX2NvbnRlbnQvam9iNjgwLTE2Ni1wLWwxZGJ1cTN2LnBuZw.png")
    fun getImage(): Call<ResponseBody>
}

object RetrofitHelper{
    val retrofitInstance = Retrofit.Builder()
        .baseUrl("https://images.rawpixel.com/image_png_800/")
        .build()
}
