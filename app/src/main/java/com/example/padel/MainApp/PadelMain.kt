package com.example.padel.MainApp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.padel.ViewModels.CalendarViewModel
import com.example.padel.composables.Home.AvailabilityButton
import com.example.padel.composables.Home.BottomNavigation
import com.example.padel.composables.Home.HourSelectionGrid
import com.example.padel.composables.Home.PadelDatesLazy
import com.example.padel.composables.Home.SideNavigation


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdaptiveNavigationScreen(navController: NavHostController) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    Scaffold(topBar = {
        if(screenWidthDp > 600){
            SideNavigation()
        }
    }, bottomBar = {
        if (screenWidthDp < 600) {
            BottomNavigation(navController = navController)
        }
    }, content = { paddingValues ->
        Column(
            modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SideNav(paddingValues = paddingValues)
        }
    })
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SideNav(paddingValues: PaddingValues = PaddingValues()) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    val density = LocalDensity.current
    val contentModifier = if (screenWidthDp < 600) {
        Modifier.padding(16.dp)
    } else {
        Modifier
            .padding()
            .fillMaxSize()
    }
    var animationForVisibility =
        slideInVertically(initialOffsetY = { with(density) { -40.dp.roundToPx() } }) + expandVertically(
            expandFrom = Alignment.Top
        ) + fadeIn(

            initialAlpha = 0.3f
        )
    Scaffold(modifier = Modifier.fillMaxSize(), content = { paddingValues ->
        Column(
            modifier = contentModifier.then(
                Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
            ),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Choose a date",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 10.dp, bottom = 5.dp)
            )
            val viewModel: CalendarViewModel = viewModel()
            PadelDatesLazy(
                modifier = Modifier, viewModel = viewModel
            )
            Text(
                "Choose a hour",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 10.dp, bottom = 5.dp, top = 40.dp)
            )
            AnimatedVisibility(
                visible = viewModel.pressedState,
                enter = animationForVisibility,
                exit = slideOutVertically() + shrinkVertically() + fadeOut()
            ) {
                HourSelectionGrid(
                    Modifier
                        .padding(start = 210.dp, end = 10.dp, top = 210.dp)
                        .border(2.dp, MaterialTheme.colorScheme.inverseOnSurface),
                    viewModel = viewModel
                )
            }
            AnimatedVisibility(
                visible = viewModel.buttonPressedState,
                enter = animationForVisibility,
                exit = slideOutVertically() + shrinkVertically() + fadeOut()
            ) {
                Box(modifier = Modifier.padding(top = 50.dp)) {
                    AvailabilityButton()
                }

            }

        }
    })
}






