package com.example.padel.composables

import android.view.MotionEvent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.data.hourItems
import com.example.padel.ViewModels.CalendarViewModel


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
    var initialColor = MaterialTheme.colorScheme.onTertiary
    val secondaryColor = MaterialTheme.colorScheme.secondary
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 100.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.padding(15.dp)
    ) {
        items(hourItems) { hourItems ->
            var color by remember { mutableStateOf(initialColor) }
            val animateColors by animateColorAsState(targetValue = color)
            var selected by remember { mutableStateOf(false) }
            val scale = animateFloatAsState(if (selected) 0.5f else 1f)
            HourSelection(hour = hourItems.hour,
                colorChanging = animateColors,
                modifier = Modifier
                    .scale(scale.value)
                    .pointerInteropFilter {
                        when (it.action) {
                            MotionEvent.ACTION_DOWN -> {
                                selected = true
                                color = if (color == initialColor) secondaryColor else initialColor
                                if (color == secondaryColor) {
                                    viewModel.buttonPressedState = true
                                }

                            }

                            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                                selected = false
                                if (color == initialColor) {
                                    viewModel.buttonPressedState = false
                                }
                            }
                        }


                        true
                    })
        }
    }
}


