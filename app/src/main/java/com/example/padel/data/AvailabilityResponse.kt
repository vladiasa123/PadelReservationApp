package com.example.padel.data

data class AvailabilityResponse (
    var hourId: Int,
    var hours: String,
    var dayId: Int,
    var availableSlots: List<String>
)