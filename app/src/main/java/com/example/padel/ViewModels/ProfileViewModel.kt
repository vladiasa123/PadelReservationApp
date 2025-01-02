package com.example.padel.ViewModels

import android.service.autofill.UserData
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.padel.api.RetrofitClient
import kotlinx.coroutines.Dispatchers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel : ViewModel() {
    var animatedState: MutableState<Boolean> = mutableStateOf(false)



}