package com.example.padel.composables.MainApp


import UpwardPopUpCard
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTransformGestures
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.padel.ViewModels.CalendarViewModel
import com.example.padel.composables.Home.HourSelectionGrid
import com.example.padel.composables.Home.PadelDatesLazy
import java.io.File


@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp)
    ) {
        SideNav()
        val viewModel: CalendarViewModel = viewModel()
        val size by animateDpAsState(
            targetValue = if (viewModel.buttonPressedState) 400.dp else 0.dp,
            animationSpec = spring(
                dampingRatio = 0.8f, stiffness = 300f
            ),
            label = ""
        )
        UpwardPopUpCard(modifier = Modifier.zIndex(2f), size)
    }
}


@Composable
fun SideNav(paddingValues: PaddingValues = PaddingValues()) {
    BackHandler(enabled = true) {
        // Prevent back press action
    }
    val viewModel: CalendarViewModel = viewModel()
    val density = LocalDensity.current
    val animationForVisibility =
        slideInVertically(initialOffsetY = { with(density) { 100.dp.roundToPx() } }) + expandVertically(
            expandFrom = Alignment.Bottom
        ) + fadeIn(
            initialAlpha = 0.3f
        )

    Box(modifier = Modifier.then(
        if (viewModel.buttonPressedState) Modifier.blur(10.dp) else Modifier
    ).pointerInput(Unit) {
            detectTransformGestures { _, _, _, _ ->

            }
        }) {
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
                    modifier = Modifier.padding(start = 10.dp, bottom = 5.dp, top = 20.dp),
                    color = Color(0xFFFFFFFF)
                )
                PadelDatesLazy(
                    modifier = Modifier, viewModel = viewModel
                )
                Text(
                    "Choose a hour",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 10.dp, bottom = 5.dp, top = 20.dp),
                    color = Color(0xFFFFFFFF)
                )
                AnimatedVisibility(
                    visible = viewModel.pressedState > 0,
                    enter = animationForVisibility,
                    exit = slideOutVertically() + shrinkVertically() + fadeOut()
                ) {
                    Column {
                        Row {
                            ElevatedButton(
                                modifier = Modifier.padding(start = 10.dp),
                                onClick = { viewModel.selectedHours = false },
                                shape = RoundedCornerShape(20),
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = Color(0xFFFFFFFF),
                                    containerColor = Color(0xFF262e3a)
                                )
                            ) {
                                Text("1 hour")
                            }
                            ElevatedButton(
                                onClick = { viewModel.selectedHours = true },
                                shape = RoundedCornerShape(20),
                                modifier = Modifier.padding(start = 10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = Color(0xFFFFFFFF),
                                    containerColor = Color(0xFF262e3a)
                                )
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
                IconToggleButton(modifier = Modifier.align(Alignment.TopEnd),
                    checked = isFullScreen,
                    onCheckedChange = {
                        isFullScreen = false
                    }) {
                    Icon(
                        Icons.Filled.Close, contentDescription = null
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






