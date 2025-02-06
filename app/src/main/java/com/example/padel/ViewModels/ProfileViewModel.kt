package com.example.padel.ViewModels
import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    var animatedState: MutableState<Boolean> = mutableStateOf(false)

    var circleAnimate:  MutableState<Boolean> = mutableStateOf(false)

    var tappedOutside: MutableState<Boolean> = mutableStateOf(false)
}