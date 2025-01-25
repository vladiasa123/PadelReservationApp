package com.example.padel.data

import java.time.LocalDate
import java.time.LocalTime
import java.time.Year
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale


data class Hours(val timeRange: String, val id: Int)


//Generates one hour intervals
fun generateHourItems(startHour: Int, endHour: Int, step: Int = 1): List<Hours> {


    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    val hourItems = mutableListOf<Hours>()
    var currentStart = LocalTime.of(startHour, 0)

    while (currentStart.hour < endHour) {
        val currentEnd = currentStart.plusHours(step.toLong())
        val timeRange = "${currentStart.format(formatter)} - ${currentEnd.format(formatter)}"
        hourItems.add(Hours(timeRange, hourItems.size + 1))
        currentStart = currentEnd
    }

    return hourItems
}


//Generates two hour intervals
fun generateTwoHourItems(startHour: Int, endHour: Int): List<Hours> {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    val twoHourItems = mutableListOf<Hours>()
    var currentStart = LocalTime.of(startHour, 0)

    while (currentStart.hour < endHour) {
        val currentEnd = currentStart.plusHours(2)
        val timeRange = "${currentStart.format(formatter)} - ${currentEnd.format(formatter)}"
        twoHourItems.add(Hours(timeRange, twoHourItems.size + 1))
        currentStart = currentEnd
    }

    return twoHourItems
}



val hourItems = generateHourItems(11, 22)
val twoHourItems = generateTwoHourItems(10, 22)




data class Calendar(
    val month: String, val dayNumber: String, val day: String, val id: Int
)


fun generateDaysForMonth(currentDate: LocalDate): List<Calendar> {
    val totalDays = 30
    return (0 until totalDays).map { dayOffset ->
        val date = currentDate.plusDays(dayOffset.toLong())
        val monthName = date.month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH).uppercase()
        val dayName = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
        Calendar(month = monthName, dayNumber = date.dayOfMonth.toString(), day = dayName, id = date.dayOfMonth)
    }
}

val calendarItems = generateDaysForMonth(currentDate = LocalDate.now())
