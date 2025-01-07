package com.example.padel.composables.Login

import android.content.Context
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.padel.ViewModels.RegisterLoginViewModel
import com.example.padel.api.RetrofitClient
import com.example.padel.composables.register.showToast
import com.example.padel.data.LoginResponse
import com.example.padel.data.UserLoginRequest
import kotlinx.coroutines.launch
import retrofit2.Response

@Composable
fun LoginPage(navController: NavHostController, registerLoginViewModel: RegisterLoginViewModel) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    fun saveTokenToPreferences(token: String) {
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("auth_token", token)
            apply()
        }
    }

    fun getTokenFromPreferences(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("auth_token", null)
    }


    fun handleLogin() {
        if (registerLoginViewModel.email.value.isEmpty() || registerLoginViewModel.password.value.isEmpty()) {
            showToast(context, "All fields are required")
            return
        }

        registerLoginViewModel.isLoading.value = true
        scope.launch {
            val loginRequest = UserLoginRequest(
                registerLoginViewModel.email.value, registerLoginViewModel.password.value
            )
            val response: Response<LoginResponse> = RetrofitClient.apiService.login(loginRequest)

            registerLoginViewModel.isLoading.value = false

            if (response.isSuccessful) {
                val loginResponse = response.body()

                loginResponse?.let {
                    val extractedToken = it.token
                    saveTokenToPreferences(extractedToken)
                   val savedToken =  getTokenFromPreferences(context)
                    Log.d("Saved Token", "Saved token: $savedToken")
                    showToast(context, "Login successful, token saved")
                    navController.navigate("ScreenB")
                } ?: run {
                    showToast(context, "Login failed: Invalid response body")
                }
            } else {
                showToast(context, "Login failed: ${response.message()}")
            }
        }
    }

    val savedToken = getTokenFromPreferences(context)
    Log.d("Saved Token", "Saved token: $savedToken")






    Box(modifier = Modifier.background(MaterialTheme.colorScheme.primary)) {
        var visible by remember { mutableStateOf(false) }
        LaunchedEffect(Unit) {
            visible = true
        }
        AnimatedVisibility(visible = visible,
            enter = slideInHorizontally(animationSpec = tween(durationMillis = 1000)) { fullWidth ->
                -fullWidth / 3
            } + fadeIn(
                animationSpec = tween(durationMillis = 200)
            ),
            exit = slideOutHorizontally(animationSpec = spring(stiffness = Spring.StiffnessHigh)) {
                200
            } + fadeOut()) {
            Column {
                Text(
                    "Welcome",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(start = 10.dp, top = 20.dp),
                    fontSize = 50.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.background
                )
                Text(
                    "Back",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(start = 10.dp, top = 10.dp),
                    fontSize = 50.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.background
                )
            }

        }
        AnimatedVisibility(visible = visible, enter = slideInVertically(
            animationSpec = tween(durationMillis = 1000)
        ) { fullHeight ->
            fullHeight
        } + fadeIn(
            animationSpec = tween(durationMillis = 200)
        ), exit = slideOutVertically(
            animationSpec = spring(stiffness = Spring.StiffnessHigh)
        ) {
            200
        } + fadeOut()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.BottomCenter)
            ) {
                Box(
                    modifier = Modifier
                        .height(550.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 50.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        TextFieldWithIcons(modifier = Modifier,
                            "Email or Username",
                            Icons.Filled.Email,
                            value = registerLoginViewModel.email.value,
                            onValueChange = { registerLoginViewModel.email.value = it })
                        TextFieldWithIcons(modifier = Modifier,
                            "Password",
                            Icons.Filled.Lock,
                            value = registerLoginViewModel.password.value,
                            onValueChange = { registerLoginViewModel.password.value = it })
                        Text("Don't have an account?",
                            modifier = Modifier
                                .padding(start = 160.dp)
                                .clickable { navController.navigate("ScreenD") })
                        Button(
                            onClick = { handleLogin() },
                            modifier = Modifier
                                .padding(top = 100.dp)
                                .width(270.dp)
                                .height(40.dp),
                            shape = RoundedCornerShape(5.dp)
                        ) {
                            Text("Sign up")
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldWithIcons(
    modifier: Modifier,
    placeHolder: String,
    Icon: ImageVector,
    value: String,
    onValueChange: (String) -> Unit
) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    var unfocusedColor = MaterialTheme.colorScheme.primary
    var focusedColor = MaterialTheme.colorScheme.tertiary
    return OutlinedTextField(
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = unfocusedColor,
            unfocusedLeadingIconColor = unfocusedColor,
            unfocusedSupportingTextColor = unfocusedColor,
            focusedBorderColor = focusedColor,
            focusedLeadingIconColor = focusedColor,
            focusedSupportingTextColor = focusedColor,
            focusedLabelColor = focusedColor
        ),
        value = value,
        leadingIcon = { Icon(imageVector = Icon, contentDescription = "emailIcon") },
        onValueChange = {
            onValueChange(it)
        },
        label = { Text(text = placeHolder) },
        placeholder = { Text(text = "Enter your e-mail") },
    )
}

