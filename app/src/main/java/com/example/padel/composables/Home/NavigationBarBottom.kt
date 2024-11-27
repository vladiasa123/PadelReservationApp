package com.example.padel.composables.Home

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.padel.data.BottomBarIcons

@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        BottomBarIcons.Home, BottomBarIcons.List, BottomBarIcons.Profile
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
                imageVector = (screen.icon), contentDescription = screen.title
            )
        },

        selected = true,

        alwaysShowLabel = true,

        onClick = { navController.navigate(screen.route) },

        colors = NavigationBarItemDefaults.colors()
    )
}
