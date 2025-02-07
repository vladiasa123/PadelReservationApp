package com.example.padel.composables.Login

import android.content.Context
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Canvas
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.padel.ViewModels.JwtTokenViewModel
import com.example.padel.ViewModels.ProfileViewModel
import com.example.padel.ViewModels.RegisterLoginViewModel
import com.example.padel.api.RetrofitClient
import com.example.padel.composables.register.showToast
import com.example.padel.data.LoginResponse
import com.example.padel.data.UserLoginRequest
import kotlinx.coroutines.launch
import retrofit2.Response

@Composable
fun LoginPage(
    navController: NavHostController,
    registerLoginViewModel: RegisterLoginViewModel,
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val jwtTokenViewModel: JwtTokenViewModel = viewModel()

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
                    jwtTokenViewModel.decodeToken(extractedToken)
                    val savedToken = getTokenFromPreferences(context)
                    Log.d("Saved Token", "Saved token: $savedToken")
                    showToast(context, "Login successful, token saved")
                    registerLoginViewModel.topAppBar.value = true
                    navController.navigate("Home")
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
    var visible by remember { mutableStateOf(false) }
    val profileViewModel: ProfileViewModel = viewModel()

    val animatedX by animateFloatAsState(
        targetValue = if (profileViewModel.circleAnimate.value) 0.7f else 0.5f,
        animationSpec = spring(dampingRatio = 0.5f, stiffness = 50f),
        label = ""
    )

    val animatedY by animateFloatAsState(
        targetValue = if (profileViewModel.circleAnimate.value) 0.3f else 0.4f,
        animationSpec = spring(dampingRatio = 0.5f, stiffness = 50f),
        label = ""
    )
    val circlePosition = Offset(animatedX, animatedY)


    Box(modifier = Modifier.background(Color(0xFF212932))) {
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
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .blur(100.dp)
            ) {
                drawOval(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFF924974), Color(0xFFe38378))
                    ), size = Size(
                        width = size.width * 2, // Adjust this for the width
                        height = 300f // Height stays the same
                    ), topLeft = Offset(
                        x = size.width * circlePosition.x - 1000f, // Offset to center it
                        y = size.height * circlePosition.y - 150f // Offset to center it
                    )
                )
            }
        }

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
                    text = "Welcome", style = TextStyle(
                        fontSize = 50.sp,
                        fontWeight = FontWeight.Bold,
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFF924974), Color(0xFFe38378))
                        )
                    ), modifier = Modifier.padding(start = 10.dp, top = 20.dp)
                )
                Text(
                    text = "Back", style = TextStyle(
                        fontSize = 50.sp,
                        fontWeight = FontWeight.Bold,
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFF924974), Color(0xFFe38378))
                        )
                    ), modifier = Modifier.padding(start = 10.dp, top = 20.dp)
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
                        .background(Color(0xFF262e3a))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 50.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        TextFieldWithIcons(modifier = Modifier,
                            isPasswordField = false,
                            placeHolder = "Email or Username",
                            Icon = Icons.Filled.Email,
                            value = registerLoginViewModel.email.value,
                            onValueChange = { registerLoginViewModel.email.value = it })
                        TextFieldWithIcons(modifier = Modifier,
                            isPasswordField = true,
                            placeHolder = "Password",
                            Icon = Icons.Filled.Lock,
                            value = registerLoginViewModel.password.value,
                            onValueChange = { registerLoginViewModel.password.value = it })
                        Text("Don't have an account?",
                            modifier = Modifier
                                .padding(start = 160.dp)
                                .clickable { navController.navigate("ScreenE") },
                            color = Color.White
                        )
                        Box(
                            modifier = Modifier
                                .padding(top = 100.dp)
                                .width(270.dp)
                                .height(40.dp)
                                .background(
                                    Brush.linearGradient(
                                        colors = listOf(
                                            Color(0xFF924974), Color(0xFFe38378)
                                        )
                                    ), shape = RoundedCornerShape(5.dp)
                                )
                        ) {
                            Button(
                                onClick = { handleLogin() },
                                modifier = Modifier.fillMaxSize(),
                                shape = RoundedCornerShape(5.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Transparent
                                )
                            ) {
                                Text("Login", color = Color.White)
                            }
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
    onValueChange: (String) -> Unit,
    isPasswordField: Boolean = false
) {
    var textColor = Color(0xFFFFFFFF)
    var focusedColor = Color(0xFF924974)

    return OutlinedTextField(
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = textColor,
            unfocusedLeadingIconColor = textColor,
            unfocusedSupportingTextColor = textColor,
            focusedBorderColor = focusedColor,
            focusedLeadingIconColor = focusedColor,
            focusedSupportingTextColor = focusedColor,
            focusedLabelColor = focusedColor,
            containerColor = Color(0xFF3f4c60)
        ),
        value = value,
        leadingIcon = { Icon(imageVector = Icon, contentDescription = "emailIcon") },
        onValueChange = {
            onValueChange(it)
        },
        label = { Text(text = placeHolder, color = textColor) },
        placeholder = { Text(text = "Enter your e-mail", color = textColor) },
        visualTransformation = if (isPasswordField) PasswordVisualTransformation() else VisualTransformation.None
    )
}

