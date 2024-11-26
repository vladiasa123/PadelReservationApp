package com.example.padel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.padel.MainApp.AdaptiveNavigationScreen
import com.example.padel.composables.LoginPage


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
                    LoginPage(navController = navController)
                }
                composable("screenB") {
                    AdaptiveNavigationScreen()
                }
            }
        }
    }
}

@PreviewScreenSizes
@Preview
@Composable
fun Adpt() {
    AdaptiveNavigationScreen()
}





