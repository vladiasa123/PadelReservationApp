package com.example.padel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.padel.ViewModels.ProfileViewModel
import com.example.padel.composables.MainApp.App


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val profileViewModel: ProfileViewModel = viewModel();

            val animatedX by animateFloatAsState(
                targetValue = if (profileViewModel.circleAnimate.value) 0.7f else 0.05f,
                animationSpec = spring(dampingRatio = 0.5f, stiffness = 50f), label = ""
            )

            val animatedY by animateFloatAsState(
                targetValue = if (profileViewModel.circleAnimate.value) 0.3f else 0.05f,
                animationSpec = spring(dampingRatio = 0.5f, stiffness = 50f), label = ""
            )
            var blur = 0.dp;


            val animateBlur by animateDpAsState(
                targetValue = if(profileViewModel.circleAnimate.value) 50.dp else 200.dp,
                animationSpec = spring(dampingRatio = 0.5f, stiffness = 50f), label = ""
            )



            val circlePosition = Offset(animatedX, animatedY)

            Box(
                modifier = Modifier
                    .background(Color(0xFF212932))
                    .fillMaxSize()
            ) {
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .blur(animateBlur)
                ) {
                    drawCircle(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFF924974), Color(0xFFe38378))
                        ),
                        radius = 250f,
                        center = Offset(
                            x = size.width * circlePosition.x,
                            y = size.height * circlePosition.y
                        )
                    )
                }

                // Your app content goes here
                App()
            }
        }
    }
}







