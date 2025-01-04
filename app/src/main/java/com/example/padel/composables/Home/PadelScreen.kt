package com.example.padel.composables.Home

import android.view.MotionEvent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.padel.ViewModels.CalendarViewModel
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale
import com.example.padel.data.calendarItems


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PadelDatesLazy(modifier: Modifier = Modifier, viewModel: CalendarViewModel) {
    val initialColor = MaterialTheme.colorScheme.onTertiary
    val secondaryColor = MaterialTheme.colorScheme.secondary
    var selectedItemIndex by remember { mutableStateOf<Int?>(null) }
    Box(
        modifier = modifier
            .padding(start = 10.dp, end = 10.dp)
            .border(
                2.dp,
                SolidColor(MaterialTheme.colorScheme.inverseOnSurface),
                shape = RoundedCornerShape(2.dp)
            ),
    ) {
        LazyRow {
            items(calendarItems) { item ->
                val isSelected = selectedItemIndex == item.id

                val backgroundColor by animateColorAsState(
                    targetValue = when {
                        isSelected -> secondaryColor
                        else -> initialColor
                    },
                    animationSpec = spring(dampingRatio = 0.4f, stiffness = 200f)
                )

                val scale by animateFloatAsState(
                    targetValue = if (isSelected) 0.9f else 1f,
                    animationSpec = spring(dampingRatio = 0.4f, stiffness = 200f)
                )

                val state = rememberLazyListState()
                val stateScrolling = state.isScrollInProgress

                CalendarItem(
                    month = item.month,
                    day = item.dayNumber,
                    dayText = item.day,
                    colorChanging = backgroundColor,
                    modifier = Modifier
                        .scale(scale)
                        .padding(5.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(backgroundColor)
                        .pointerInteropFilter {
                            if (!stateScrolling) {
                                when (it.action) {
                                    MotionEvent.ACTION_DOWN -> {
                                        if (isSelected) {
                                            selectedItemIndex = null
                                            viewModel.pressedState = false
                                        } else {
                                            selectedItemIndex = item.id
                                            viewModel.pressedState = true
                                            viewModel.updateDate(item.id)
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
                        }
                )
            }
        }
    }
}


@Preview
@Composable
fun PreviewCalendar() {
    val viewModel = CalendarViewModel()
    PadelDatesLazy(viewModel = viewModel)
}




