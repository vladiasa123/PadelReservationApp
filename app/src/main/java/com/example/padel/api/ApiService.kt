package com.example.padel.api

import com.example.padel.data.UserSignupRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/req/signup")
    suspend fun signup(@Body user: UserSignupRequest): Response<UserSignupRequest>
}