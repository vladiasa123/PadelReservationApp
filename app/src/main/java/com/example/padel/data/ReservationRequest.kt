package com.example.padel.data

data class ReservationRequest(
    val hour: String,
    val day: String,
    val dayId: String,
    val userId: String
)