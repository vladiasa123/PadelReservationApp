package com.example.data

// Data class with the month field as a String to represent month names.
data class Calendar(
    val month: String,  // Month as String (e.g., "FEB", "JAN")
    val dayNumber: String,  // Day number as String (e.g., "1", "2")
    val day: String  // Day of the week (e.g., "Mon", "Tue")
)

// Initialize the list with month names like "JAN", "FEB", etc.
val calendarItems = listOf(
    Calendar("JAN", "1", "Mon"),
    Calendar("FEB", "2", "Tue"),
    Calendar("MAR", "3", "Wed"),
    Calendar("APR", "4", "Thu"),
    Calendar("MAY", "5", "Fri"),
    Calendar("JUN", "6", "Sat"),
    Calendar("JUL", "7", "Sun")
)
