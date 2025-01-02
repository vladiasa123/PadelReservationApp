package com.example.padel.api

import android.service.autofill.UserData
import com.example.padel.data.LoginResponse
import com.example.padel.data.UserAccount
import com.example.padel.data.UserLoginRequest
import com.example.padel.data.UserSignupRequest
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("/req/signup")
    suspend fun signup(@Body user: UserSignupRequest): Response<UserSignupRequest>

    @POST("/req/login")
    suspend fun login(@Body user: UserLoginRequest): Response<LoginResponse>
}