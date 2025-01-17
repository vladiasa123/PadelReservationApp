package com.example.padel.composables.Home

import android.util.Log
import android.view.MotionEvent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.padel.data.twoHourItems


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HourSelection(
    modifier: Modifier = Modifier, hour: String, colorChanging: Color, textColor: Color
) {
    Box(
        modifier = Modifier
            .width(5.dp)
            .height(55.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(colorChanging), contentAlignment = Alignment.Center


    ) {
            Text(
                hour,
                modifier = modifier.padding(top = 2.dp, bottom = 2.dp),
                textAlign = TextAlign.Center,
                color = textColor
            )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HourSelectionGrid(modifier: Modifier, viewModel: CalendarViewModel) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    var selectedItemIndex by remember { mutableStateOf<Int?>(null) }
    var deactivatedItems by remember { mutableStateOf(setOf<String?>()) }
    var sizeAnimation by remember { mutableStateOf(false) }

    fun clearDeactivatedItems() {
        deactivatedItems = emptySet()
    }

    fun deactivateItem(hour: List<String>) {
        deactivatedItems =
            (deactivatedItems + viewModel.unavailableSlots) as MutableSet<String?>
    }


    LaunchedEffect(viewModel.reservationPaid) {
        if (viewModel.unavailableSlots.isEmpty()) {
            clearDeactivatedItems()
            Log.d("items", deactivatedItems.toString())
        } else {
            clearDeactivatedItems()
            deactivateItem(viewModel.unavailableSlots)
            selectedItemIndex = null
        }

    }




    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 100.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = if (screenWidthDp > 600) {
            Modifier.padding(100.dp)
        } else {
            Modifier.padding(15.dp).border(2.dp, MaterialTheme.colorScheme.inverseOnSurface).padding(15.dp)
        }
    ) {
        items(if (viewModel.selectedHours) twoHourItems else hourItems) { hourItems ->
            var initialColor = MaterialTheme.colorScheme.onTertiary
            val secondaryColor = MaterialTheme.colorScheme.secondary
            var isSelected = selectedItemIndex == hourItems.id
            var isDeactivated = hourItems.timeRange in deactivatedItems

            val backgroundColor by animateColorAsState(
                targetValue = when {
                    isDeactivated -> Color(0xFFF5F5F5)
                    isSelected -> secondaryColor
                    else -> initialColor
                }, animationSpec = spring(dampingRatio = 0.4f, stiffness = 200f)
            )

            val textColor by animateColorAsState(
                targetValue = when {
                    isSelected -> Color.White
                    else -> Color.Black
                }, animationSpec = spring(dampingRatio = 0.4f, stiffness = 200f)
            )


            LaunchedEffect(viewModel.recomposeCalendar) {
                isSelected = false
            }

            val scale by animateFloatAsState(
                targetValue = if (isSelected) 0.9f else 1f,
                animationSpec = spring(dampingRatio = 0.4f, stiffness = 200f)
            )

            val state = rememberLazyListState()
            val stateScrolling = state.isScrollInProgress
            HourSelection(hour = hourItems.timeRange,
                colorChanging = backgroundColor,
                textColor = textColor,
                modifier = Modifier
                    .scale(scale)
                    .pointerInteropFilter {
                        if (!stateScrolling) {
                            when (it.action) {
                                MotionEvent.ACTION_DOWN -> {
                                    if (isDeactivated and deactivatedItems.isNotEmpty()) {
                                        return@pointerInteropFilter false
                                    }
                                    if (isSelected) {
                                        selectedItemIndex = null
                                    } else {
                                        selectedItemIndex = hourItems.id
                                        sizeAnimation = true
                                        viewModel.pressedState = hourItems.id
                                        viewModel.buttonPressedState = true
                                        viewModel.updateHour(hourItems.id)
                                    }
                                }

                                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                                    if (!isSelected && backgroundColor == initialColor) {
                                        sizeAnimation = false
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