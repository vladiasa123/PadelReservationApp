package com.example.padel.MainApp

import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.padel.R
import com.example.padel.ViewModels.CalendarViewModel
import com.example.padel.ViewModels.QRViewModel
import com.example.padel.composables.Home.AvailabilityButton
import com.example.padel.composables.Home.BottomNavigation
import com.example.padel.composables.Home.HourSelectionGrid
import com.example.padel.composables.Home.PadelDatesLazy
import com.example.padel.composables.Home.SideNavigation
import java.io.File


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdaptiveNavigationScreen(navController: NavHostController) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    Scaffold(topBar = {
        if (screenWidthDp > 600) {
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
    val viewModel: CalendarViewModel = viewModel()
    val qrViewModel: QRViewModel = viewModel()
    val density = LocalDensity.current
    var animationForVisibility =
        slideInVertically(initialOffsetY = { with(density) { -40.dp.roundToPx() } }) + expandVertically(
            expandFrom = Alignment.Top
        ) + fadeIn(

            initialAlpha = 0.3f
        )
    var animationForVisibilityQR =
        slideInVertically(initialOffsetY = { with(density) { -40.dp.roundToPx() } }) + expandVertically(
            expandFrom = Alignment.Top
        ) + fadeIn(

            initialAlpha = 0.3f
        )
    AnimatedVisibility(
        visible = qrViewModel.QrCodeShowing.collectAsState().value == 1,
        enter = animationForVisibilityQR,
        exit = slideOutVertically() + shrinkVertically() + fadeOut()
    ) {
        Box() {
            val qrViewModel: QRViewModel = viewModel()
            val qrCodeShowing = qrViewModel.QrCodeShowing.collectAsState().value
            Log.d("qr", "qr is $qrCodeShowing")
            if (qrCodeShowing == 1) {
                ImageQr()
            }
        }
    }
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    val contentModifier = if (screenWidthDp < 600) {
        Modifier.padding(16.dp)
    } else {
        Modifier
            .padding()
            .fillMaxSize()
    }
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
                Column {
                    Row {
                        Button(onClick = {viewModel.selectedHours = false}, border = BorderStroke(2.dp, MaterialTheme.colorScheme.inverseOnSurface), shape = RoundedCornerShape(20)) {
                            Text("1 hour")
                        }
                        Button(onClick = {viewModel.selectedHours = true}, border = BorderStroke(2.dp, MaterialTheme.colorScheme.inverseOnSurface), shape = RoundedCornerShape(20), modifier = Modifier.padding(start = 10.dp)) {
                            Text("2 hours")
                        }
                    }
                    HourSelectionGrid(
                        Modifier
                            .padding(start = 210.dp, end = 10.dp, top = 210.dp)
                            .border(2.dp, MaterialTheme.colorScheme.inverseOnSurface),
                        viewModel = viewModel
                    )
                }
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

@Composable
fun ImageQr(modifier: Modifier = Modifier) {
    var isFullScreen by remember { mutableStateOf(false) }
    val animationForVisibility by animateFloatAsState(
        targetValue = if (isFullScreen) 1f else 0.5f,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
    )
    val imageLocation = "/data/data/com.example.padel/files/reservation.png"
    Log.d("ImageQr", "Image Path: $imageLocation")
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .border(2.dp, MaterialTheme.colorScheme.inverseOnSurface)
            .padding(start = 10.dp, end = 10.dp, bottom = 30.dp)
            .fillMaxWidth()
    ) {
        androidx.compose.animation.AnimatedVisibility(visible = isFullScreen) {
            Box(modifier = Modifier.fillMaxWidth()) {
                IconToggleButton(
                    modifier = Modifier.align(Alignment.TopEnd),
                    checked = isFullScreen,
                    onCheckedChange = {
                        isFullScreen = false
                    }
                ) {
                    Icon(
                        Icons.Filled.Close,
                        contentDescription = null
                    )
                }
            }
        }

        Image(
            rememberAsyncImagePainter(File(imageLocation)),
            contentDescription = "rjt",
            if (!isFullScreen) {
                modifier.size(200.dp)
            } else {
                modifier.fillMaxSize(animationForVisibility)
            }
        )
        Button(onClick = { isFullScreen = true }) {
            Text("Increase size")
        }
    }
}

@Preview
@Composable
private fun ImageQrs() {
    ImageQr()
}






