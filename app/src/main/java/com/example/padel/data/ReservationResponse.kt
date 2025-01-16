package com.example.padel.data

data class ReservationResponse (
    val message: String,
    val userReservations: MutableList<String>
)
