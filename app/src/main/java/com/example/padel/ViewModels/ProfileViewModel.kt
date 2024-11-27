package com.example.padel.ViewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.lifecycle.ViewModel

class ProfileViewModel: ViewModel(){
    var animatedState by  mutableStateOf(false)


}