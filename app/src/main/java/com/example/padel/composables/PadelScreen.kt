package com.example.padel.composables

import android.view.MotionEvent
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


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PadelDatesLazy(modifier: Modifier = Modifier) {
    val initialColor = MaterialTheme.colorScheme.onTertiary
    val secondaryColor = MaterialTheme.colorScheme.secondary
    var selected by remember { mutableStateOf(false) }
    var color by remember { mutableStateOf(initialColor) }
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
                CalendarItem(month = item.month,
                    day = item.dayNumber,
                    dayText = item.day,
                    colorChanging = color,
                    selected = selected,
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
                                    }
                                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                                        selected = false
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
