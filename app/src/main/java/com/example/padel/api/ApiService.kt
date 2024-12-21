package com.example.padel.api

import com.example.padel.data.LoginResponse
import com.example.padel.data.UserLoginRequest
import com.example.padel.data.UserSignupRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/req/signup")
    suspend fun signup(@Body user: UserSignupRequest): Response<UserSignupRequest>

    @POST("/req/login")
    suspend fun login(@Body user: UserLoginRequest): Response<LoginResponse>
}