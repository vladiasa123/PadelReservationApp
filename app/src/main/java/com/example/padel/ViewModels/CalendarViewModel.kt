package com.example.padel.ViewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel

class CalendarViewModel() : ViewModel() {

   var pressedState by mutableStateOf(false)
   fun pressedState(state: Boolean){
      pressedState = state
   }
}

