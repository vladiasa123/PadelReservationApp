package com.example.padel

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.padel.composables.MainApp.AdaptiveNavigationScreen
import com.example.padel.ViewModels.JwtTokenViewModel
import com.example.padel.ViewModels.ProfileViewModel
import com.example.padel.ViewModels.RegisterLoginViewModel
import com.example.padel.composables.Login.LoginPage
import com.example.padel.composables.Profile.ProfilePage
import com.example.padel.composables.register.RegisterPage


class MainActivity : ComponentActivity() {
    val SCREEN_TRANSITION_MILLIS = 700

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "screenA", enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start, tween(
                        SCREEN_TRANSITION_MILLIS
                    )
                )
            },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Start, tween(
                            SCREEN_TRANSITION_MILLIS
                        )
                    )
                },
                popEnterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.End, tween(
                            SCREEN_TRANSITION_MILLIS
                        )
                    )
                },
                popExitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.End, tween(
                            SCREEN_TRANSITION_MILLIS
                        )
                    )
                }
            ) {
                composable("screenA") {
                    LoginPage(navController = navController, registerLoginViewModel = RegisterLoginViewModel())
                }
                composable("screenB") {
                    AdaptiveNavigationScreen(navController = navController)
                }
                composable("screenC") {
                    val viewModel: ProfileViewModel = viewModel()
                    val jwtViewModel: JwtTokenViewModel = viewModel()


                    ProfilePage(viewModel = viewModel, jwtViewModel = jwtViewModel)
                }
                composable("screenD") {
                    RegisterPage(navController = navController, registerLoginViewModel = RegisterLoginViewModel())
                }
            }
        }
    }
}






