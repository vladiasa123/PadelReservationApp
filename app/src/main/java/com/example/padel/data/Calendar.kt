package com.example.padel.data

import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale


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