package com.example.padel.data

import java.time.LocalDate
import java.time.LocalTime
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

fun generateDaysForMonth(month: Int, year: Int, currentDate: LocalDate): List<Calendar> {
    val monthName =
        LocalDate.of(year, month, 1).month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
            .uppercase()
    val totalDays =
        LocalDate.of(year, month, 1).month.length(LocalDate.of(year, month, 1).isLeapYear)

    return (1..totalDays).map { day ->
        val date = LocalDate.of(year, month, day)
        if (!date.isBefore(currentDate)) {
            val dayName = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
            Calendar(month = monthName, dayNumber = day.toString(), day = dayName, id = day)
        } else {
            null
        }
    }.filterNotNull()
}

val calendarItems = generateDaysForMonth(
    month = LocalDate.now().monthValue, year = LocalDate.now().year, currentDate = LocalDate.now()
)