package com.example.padel.ViewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.padel.data.calendarItems
import com.example.padel.data.hourItems
import com.example.padel.data.twoHourItems

class CalendarViewModel : ViewModel() {

    var datesAdded by mutableStateOf(false)
    var firstLaunchedEffects by  mutableStateOf(false)


    var unavailableSlots: MutableList<String> = mutableListOf()
        private set


    fun addUnavailableSlot(slot: List<String>) {
        unavailableSlots.clear()
        unavailableSlots.addAll(slot)
    }




    var selectedDay by mutableStateOf<String?>(null)
    var selectedHour by mutableStateOf<String?>(null)
    var selectedDayId by mutableStateOf<Int?>(null)
    var selectedHours by mutableStateOf(false)
    var reservationPaid by mutableStateOf(0)
    var hoursId by mutableStateOf(0)
    var dayId by mutableStateOf(0)
    var recomposeCalendar by mutableStateOf(0)


    fun updateDate(id: Int) {
        val item = calendarItems.find { it.id == id }
        selectedDay = item?.dayNumber
        Log.d("CalendarViewModel", "Selected day: $selectedDay")
    }

    fun updateHour(id: Int) {
        if (!selectedHours) {
            val item = hourItems.find { it.id == id }
            selectedHour = item?.timeRange
        } else {
            val item = twoHourItems.find { it.id == id }
            selectedHour = item?.timeRange
        }
    }

    fun updateDayId(id: Int) {
        val item = calendarItems.find { it.id == id }
        selectedDayId = item?.id
        Log.d("CalendarViewModel", "Selected id: $selectedDayId")
    }


    var buttonPressedState by mutableStateOf(false)
    var pressedState by mutableStateOf(0)
    fun pressedState(state: Int) {
        pressedState = state
    }
}

