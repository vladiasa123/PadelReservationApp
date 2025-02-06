package com.example.padel.composables.Home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun CalendarItem(modifier: Modifier = Modifier, month: String, day: String, dayText: String, colorChanging: Brush, textColor: Color ) {
    val color = remember { mutableStateOf(colorChanging) }
    Column(
        modifier = modifier
            .width(65.dp)
            .height(65.dp)
            .background(colorChanging),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            dayText,
            style = MaterialTheme.typography.displayMedium,
            fontSize = 12.sp,
            color = textColor
        )
        Text(
            day, fontSize = 17.sp, style = MaterialTheme.typography.displayMedium, color = textColor
        )
        Text(
            month,
            style = MaterialTheme.typography.displayMedium,
            fontSize = 12.sp,
            color = textColor

        )
    }
}