package com.example.padel.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }



    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .followRedirects(false)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.0.31:8080")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}