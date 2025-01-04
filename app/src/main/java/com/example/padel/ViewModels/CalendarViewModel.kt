package com.example.padel.ViewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.padel.data.Calendar
import com.example.padel.data.calendarItems
import com.example.padel.data.hourItems

class CalendarViewModel : ViewModel() {

    var selectedDay by mutableStateOf<String?>(null)
    var selectedHour by mutableStateOf<String?>(null)

    fun updateDate(id: Int) {
        val item = calendarItems.find { it.id == id }
        selectedDay = item?.dayNumber
        Log.d("CalendarViewModel", "Selected day: $selectedDay")
    }

    fun updateHour(id: Int){
        val item = hourItems.find { it.id == id }
        selectedHour = item?.hour
        Log.d("CalendarViewModel", "Selected hour: $selectedHour")
    }


    var buttonPressedState by mutableStateOf(false)
    var pressedState by mutableStateOf(false)
    fun pressedState(state: Boolean) {
        pressedState = state
    }
}

