package com.example.padel.data

data class Hours(
    val hour: String,
    val id: Int
)

val hourItems = listOf(
    Hours("10:00 - 11:00", 1),
    Hours("11:00 - 12:00", 2),
    Hours("12:00 - 13:00", 3),
    Hours("13:00 - 14:00", 4),
    Hours("14:00 - 15:00", 5),
    Hours("15:00 - 16:00", 6),
    Hours("16:00 - 17:00", 7),
    Hours("17:00 - 18:00", 8),
    Hours("18:00 - 19:00", 9),
    Hours("19:00 - 20:00", 10),
    Hours("20:00 - 21:00", 11)
)

val twoHourItems = listOf(
    Hours("10:00 - 12:00", 2_1),
    Hours("12:00 - 14:00", 2_2),
    Hours("14:00 - 16:00", 2_3),
    Hours("16:00 - 18:00", 2_4),
    Hours("18:00 - 20:00", 2_5),
    Hours("20:00 - 22:00", 2_6)
)


