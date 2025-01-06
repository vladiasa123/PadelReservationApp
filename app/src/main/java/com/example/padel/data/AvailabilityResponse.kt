package com.example.padel.data

data class AvailabilityResponse (
    var hourId: Int,
    var availableSlots: List<String>
)