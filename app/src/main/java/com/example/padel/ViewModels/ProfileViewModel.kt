package com.example.padel.ViewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {
    var animatedState: MutableState<Boolean> = mutableStateOf(false)


}