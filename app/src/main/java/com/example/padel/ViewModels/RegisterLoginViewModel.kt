package com.example.padel.ViewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.padel.api.RetrofitClient.apiService
import kotlinx.coroutines.launch

class RegisterLoginViewModel: ViewModel() {
    var username: MutableState<String> = mutableStateOf("")
    var email: MutableState<String> = mutableStateOf("")
    var password: MutableState<String> = mutableStateOf("")
    var isLoading: MutableState<Boolean> = mutableStateOf(false)


    fun fetchExampleData() {
        viewModelScope.launch {
            try {
                val response = apiService.login()
                if (response.isSuccessful) {
                    val data = response.body()
                    println("Fetched data: $data")
                } else {
                    println("Error: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                println("Exception: $e")
            }
        }
    }
}