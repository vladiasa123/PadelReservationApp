package com.example.padel.ViewModels
import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    var animatedState: MutableState<Boolean> = mutableStateOf(false)
}