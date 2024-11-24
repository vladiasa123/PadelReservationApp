package com.example.padel.composables

import android.view.MotionEvent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.unit.dp
import com.example.data.calendarItems
import com.example.padel.ViewModels.CalendarViewModel


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PadelDatesLazy(modifier: Modifier = Modifier, viewModel: CalendarViewModel) {
    val initialColor = MaterialTheme.colorScheme.onTertiary
    val secondaryColor = MaterialTheme.colorScheme.secondary
    var selected by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp)
            .border(
                2.dp,
                SolidColor(MaterialTheme.colorScheme.inverseOnSurface),
                shape = RoundedCornerShape(2.dp)
            )
    ) {
        LazyRow {
            items(calendarItems) { item ->
                var color by remember { mutableStateOf(initialColor) }
                var selected by remember { mutableStateOf(false) }
                val scale = animateFloatAsState(if (selected) 0.5f else 1f)
                val state = rememberLazyListState()
                val stateScrolling = state.isScrollInProgress
                val animateColors by animateColorAsState(targetValue = color)
                CalendarItem(month = item.month,
                    day = item.dayNumber,
                    dayText = item.day,
                    colorChanging = animateColors,
                    modifier = Modifier
                        .scale(scale.value)
                        .padding(5.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .pointerInteropFilter {
                            if (!stateScrolling) { // Ignore pointer events while scrolling
                                when (it.action) {
                                    MotionEvent.ACTION_DOWN -> {
                                        selected = true
                                        color = if (color == initialColor) secondaryColor else initialColor
                                        if (color == secondaryColor) {
                                            viewModel.pressedState = true
                                        }

                                    }
                                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                                        selected = false
                                        if (color == initialColor) {
                                            viewModel.pressedState = false
                                        }

                                    }
                                }

                            }
                            true
                        }

                )
            }
        }
    }
}




