package com.example.padel.data

data class UserSignupRequest (
    val id: Long,
    val username: String,
    val email: String,
    val password: String,
)