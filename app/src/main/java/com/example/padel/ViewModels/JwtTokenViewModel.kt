package com.example.padel.ViewModels

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import com.example.padel.api.RetrofitClient
import com.example.padel.data.PageAcces
import com.example.padel.data.PageAccesResponse
import com.example.padel.composables.register.showToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response
import java.util.Base64
import javax.crypto.SecretKey


class JwtTokenViewModel(application: Application): AndroidViewModel(application) {
    private var sharedPreferences: SharedPreferences

    var usersId: MutableState<String> = mutableStateOf("")


    init {
        sharedPreferences = application.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    }

    suspend fun getToken(): String? {
        return withContext(Dispatchers.IO) {
            sharedPreferences.getString("auth_token", null)
        }
    }

    fun verifyAcces(token: String?, context: Context, scope: CoroutineScope) {
        if (token == null) {
            showToast(context, "You don't have acces to this page")
            return
        }
        scope.launch {
            val accesRequest = PageAcces(
                token
            )
            val response: Response<PageAccesResponse> =
                RetrofitClient.apiService.acces(accesRequest)
            if (response.isSuccessful) {
                val accesResponse = response.body()
            } else {
                showToast(context, "Acces not granted: ${response.message()}")
            }
        }
    }

    fun decodeToken(jwt: String): String {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return "Requires SDK 26"

        val parts = jwt.split(".")

        return try {
            val charset = charset("UTF-8")

            val header =
                String(Base64.getUrlDecoder().decode(parts[0].toByteArray(charset)), charset)
            val payload =
                String(Base64.getUrlDecoder().decode(parts[1].toByteArray(charset)), charset)

            val jsonPayload = JSONObject(payload)

            val email = jsonPayload.optString("email", "No email")
            val userId = jsonPayload.optString("userId", "No user_id")
            usersId.value = userId
            val expiration = jsonPayload.optLong("expiration", -1)
            return "Header: $header\nEmail: $email\nUser ID: $userId\nExpiration: $expiration"
        } catch (e: Exception) {
            "Error parsing JWT: $e"
        }
    }
}