package com.example.padel.ViewModels

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import com.example.padel.api.RetrofitClient
import com.example.padel.data.PageAcces
import com.example.padel.data.PageAccesResponse
import com.example.padel.register.showToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.crypto.SecretKey


class JwtTokenViewModel(application: Application): AndroidViewModel(application) {
    private var sharedPreferences: SharedPreferences

    init {
        sharedPreferences = application.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    }

    suspend fun getToken(): String? {
        return withContext(Dispatchers.IO) {
            sharedPreferences.getString("auth_token", null)
        }
    }

    fun verifyAcces(token: String?, context: Context, scope: CoroutineScope){
        if(token == null){
            showToast(context,"You don't have acces to this page")
            return
        }
        scope.launch{
            val accesRequest = PageAcces(
                token
            )
            val response: Response<PageAccesResponse> = RetrofitClient.apiService.acces(accesRequest)
            if(response.isSuccessful){
                val accesResponse = response.body()
            }else{
                showToast(context, "Acces not granted: ${response.message()}")
            }
        }
    }
}