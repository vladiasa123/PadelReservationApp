package com.example.padel.composables.MainApp


import UpwardPopUpCard
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.padel.ViewModels.CalendarViewModel
import com.example.padel.ViewModels.ProfileViewModel
import com.example.padel.ViewModels.QRViewModel
import com.example.padel.composables.Home.HourSelectionGrid
import com.example.padel.composables.Home.PadelDatesLazy
import java.io.File


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    val viewModel: CalendarViewModel = viewModel()
    val density = LocalDensity.current
    Box(modifier = Modifier.fillMaxSize()) {
        SideNav()
        AnimatedVisibility(
            visible = viewModel.buttonPressedState,
            enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(durationMillis = 500)
            ),
            exit = slideOutVertically(
                targetOffsetY = { it },
                animationSpec = tween(durationMillis = 500)
            )
        ) {
            UpwardPopUpCard()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SideNav(paddingValues: PaddingValues = PaddingValues(), modifier: Modifier = Modifier) {
    val viewModel: CalendarViewModel = viewModel()
    val profileViewModel: ProfileViewModel = viewModel()
    val qrViewModel: QRViewModel = viewModel()
    val density = LocalDensity.current

    val animationForVisibility = slideInVertically(
        initialOffsetY = { with(density) { 100.dp.roundToPx() } }
    ) + expandVertically(expandFrom = Alignment.Bottom) + fadeIn(initialAlpha = 0.3f)

    val animationForVisibilityQR = slideInVertically(
        initialOffsetY = { with(density) { -40.dp.roundToPx() } }
    ) + expandVertically(expandFrom = Alignment.Top) + fadeIn(initialAlpha = 0.3f)
    Box(modifier = Modifier.then(
        if (viewModel.buttonPressedState) Modifier.blur(10.dp) else Modifier
    )) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth()
                    .padding(paddingValues)
                    .heightIn(min = 0.dp, max = 800.dp)
            ) {
                Text(
                    "Choose a date",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 10.dp, bottom = 5.dp, top = 20.dp)
                )
                PadelDatesLazy(
                    modifier = Modifier, viewModel = viewModel
                )
                Text(
                    "Choose a hour",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 10.dp, bottom = 5.dp, top = 20.dp)
                )
                AnimatedVisibility(
                    visible = viewModel.pressedState > 0,
                    enter = animationForVisibility,
                    exit = slideOutVertically() + shrinkVertically() + fadeOut()
                ) {
                    Column {
                        Row {
                            Button(
                                modifier = Modifier.padding(start = 10.dp),
                                onClick = { viewModel.selectedHours = false },
                                border = BorderStroke(
                                    2.dp,
                                    MaterialTheme.colorScheme.inverseOnSurface
                                ),
                                shape = RoundedCornerShape(20)
                            ) {
                                Text("1 hour")
                            }
                            Button(
                                onClick = { viewModel.selectedHours = true },
                                border = BorderStroke(
                                    2.dp,
                                    MaterialTheme.colorScheme.inverseOnSurface
                                ),
                                shape = RoundedCornerShape(20),
                                modifier = Modifier.padding(start = 10.dp)
                            ) {
                                Text("2 hours")
                            }
                        }
                        HourSelectionGrid(
                            Modifier
                                .padding(10.dp)
                                .border(2.dp, MaterialTheme.colorScheme.inverseOnSurface),
                            viewModel = viewModel,
                        )
                    }
                }
            }
        }
    }
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
    SideNav()
}






