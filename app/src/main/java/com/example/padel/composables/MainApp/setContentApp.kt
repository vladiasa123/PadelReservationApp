package com.example.padel.composables.MainApp

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.padel.ViewModels.JwtTokenViewModel
import com.example.padel.ViewModels.ProfileViewModel
import com.example.padel.ViewModels.RegisterLoginViewModel
import com.example.padel.composables.Home.BottomNavigation
import com.example.padel.composables.Login.LoginPage
import com.example.padel.composables.Profile.ProfileScreen
import com.example.padel.composables.register.RegisterPage
import com.example.padel.composables.reservationCard.ReservationsScreen

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val navController = rememberNavController()
    val SCREEN_TRANSITION_MILLIS = 700
    var screenWithoutBottomBar by mutableStateOf(false)

    val currentBackStackEntry by navController.currentBackStackEntryFlow.collectAsState(initial = null)
    val currentRoute = currentBackStackEntry?.destination?.route


        if (currentRoute != null) {
            Log.d("route", currentRoute)
        }
        if(currentRoute == "screenA"){
            screenWithoutBottomBar = true
        }




    Scaffold(
        bottomBar = {
            if(!screenWithoutBottomBar){
                BottomNavigation(navController = navController)
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            NavHost(
                navController = navController,
                startDestination = "screenA",
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Start,
                        tween(SCREEN_TRANSITION_MILLIS)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Start,
                        tween(SCREEN_TRANSITION_MILLIS)
                    )
                },
                popEnterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.End,
                        tween(SCREEN_TRANSITION_MILLIS)
                    )
                },
                popExitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.End,
                        tween(SCREEN_TRANSITION_MILLIS)
                    )
                }
            ) {
                composable("screenA") {
                    LoginPage(
                        navController = navController,
                        registerLoginViewModel = RegisterLoginViewModel()
                    )
                }
                composable("screenB") {
                    HomeScreen()
                }
                composable("screenC") {
                    ProfileScreen(navController = navController)
                }
                composable("screenD") {
                    ReservationsScreen()
                }
                composable("screenE") {
                    val registerViewModel: RegisterLoginViewModel = viewModel()
                    RegisterPage(
                        navController = navController,
                        registerLoginViewModel = registerViewModel
                    )
                }
            }
        }
    }
}