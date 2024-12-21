package com.example.padel.data

data class LoginResponse(
    val message: String,
    val token: String,
    val user: UserLoginRequest
)
