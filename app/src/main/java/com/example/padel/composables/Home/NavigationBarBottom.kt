package com.example.padel.composables.Home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.padel.data.BottomBarIcons

@Composable
fun BottomNavigation(navController: NavController, modifier: Modifier = Modifier) {
    val items = listOf(
        BottomBarIcons.Home, BottomBarIcons.Booking, BottomBarIcons.MyReservations,
    )
    NavigationBar {
        items.forEach { item ->
            AddItem(
                screen = item,
                navController = navController,
            )
        }
    }
}


@Composable
fun RowScope.AddItem(
    screen: BottomBarIcons,
    navController: NavController
) {

    NavigationBarItem(
        label = {
            Text(text = screen.title)
        },
        icon = {
            Icon(
                imageVector = (screen.icon), contentDescription = screen.title,
            )
        },

        selected = true,

        alwaysShowLabel = true,

        onClick = { navController.navigate(screen.route) },

        colors = NavigationBarItemDefaults.colors(),
    )
}
