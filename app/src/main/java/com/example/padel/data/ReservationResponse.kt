package com.example.padel.data

data class ReservationResponse (
    val message: String,
    val userSlots: MutableList<String>,
    val userDays: MutableList<String>
)
