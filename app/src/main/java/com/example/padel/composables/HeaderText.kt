package com.example.padel.composables

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.padel.ViewModels.ProfileViewModel

@Composable
fun HeaderText(viewModel: ProfileViewModel) {
    val offsetX by animateDpAsState(
        targetValue = if (!viewModel.animatedState.value) (0).dp else (-300).dp,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
        label = ""
    )
    Column(modifier = Modifier.offset(x = offsetX)) {
        Text(
            "Welcome",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(start = 10.dp, top = 20.dp),
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.background
        )
        Text(
            "Back",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(start = 10.dp, top = 10.dp),
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.background
        )

    }
}