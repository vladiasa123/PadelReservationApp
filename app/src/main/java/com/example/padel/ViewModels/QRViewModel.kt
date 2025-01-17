package com.example.padel.ViewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class QRViewModel: ViewModel() {
    private val _qrCodeShowing = MutableStateFlow(0)

    fun updateQrCodeShowing(state: Int){
        _qrCodeShowing.value = state
    }
}