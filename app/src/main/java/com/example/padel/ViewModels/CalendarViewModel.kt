package com.example.padel.ViewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CalendarViewModel : ViewModel() {


    var buttonPressedState by mutableStateOf(false)
    var pressedState by mutableStateOf(false)
    fun pressedState(state: Boolean) {
        pressedState = state
    }
}

