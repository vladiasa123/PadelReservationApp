package com.example.padel.composables.Home

import android.view.MotionEvent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.padel.ViewModels.CalendarViewModel
import com.example.padel.data.hourItems


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HourSelection(modifier: Modifier = Modifier, hour: String, colorChanging: Color) {
    Box(
        modifier = Modifier
            .width(5.dp)
            .height(25.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(colorChanging), contentAlignment = Alignment.Center


    ) {
        Text(
            hour,
            modifier = modifier.padding(top = 2.dp, bottom = 2.dp),
            textAlign = TextAlign.Center,
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HourSelectionGrid(modifier: Modifier, viewModel: CalendarViewModel) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    var selectedItemIndex by remember { mutableStateOf<Int?>(null) }
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 100.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = if (screenWidthDp > 600) {
            Modifier.padding(100.dp)
        } else {
            Modifier.padding(15.dp)
        }
    ) {
        items(hourItems) { hourItems ->
            var initialColor = MaterialTheme.colorScheme.onTertiary
            val secondaryColor = MaterialTheme.colorScheme.secondary
            val isSelected = selectedItemIndex == hourItems.id
            val backgroundColor by animateColorAsState(
                targetValue = when {
                    isSelected -> secondaryColor
                    else -> initialColor
                },
                animationSpec = spring(dampingRatio = 0.4f, stiffness = 200f)
            )
            var selected by remember { mutableStateOf(false) }
            val scale = animateFloatAsState(if (selected) 0.5f else 1f)
            var color by remember { mutableStateOf(initialColor) }
            val animateColors by animateColorAsState(targetValue = color)
            val state = rememberLazyListState()
            val stateScrolling = state.isScrollInProgress
            HourSelection(hour = hourItems.hour,
                colorChanging = backgroundColor,
                modifier = Modifier
                    .scale(scale.value)
                    .pointerInteropFilter {
                        if (!stateScrolling) {
                            when (it.action) {
                                MotionEvent.ACTION_DOWN -> {
                                    if (isSelected) {
                                        selectedItemIndex = null
                                    } else {
                                        selectedItemIndex = hourItems.id
                                        viewModel.pressedState = true
                                        viewModel.buttonPressedState = true
                                        viewModel.updateHour(hourItems.id)
                                    }
                                }

                                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                                    if (!isSelected && backgroundColor == initialColor) {
                                        viewModel.pressedState = false
                                    }
                                }
                            }
                        }
                        true
                    })
        }
    }
}

@Preview
@Composable
private fun HourSelectionPreview() {
    val viewModel = CalendarViewModel()
    val modifier = Modifier
    HourSelectionGrid(modifier, viewModel)
}


