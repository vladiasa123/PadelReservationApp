package com.example.padel.composables

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.padel.data.BottomBarIcons

@Composable
fun BottomNavigation() {
    val items = listOf(
        BottomBarIcons.Home, BottomBarIcons.List, BottomBarIcons.Profile
    )

    NavigationBar {
        items.forEach { item ->
            AddItem(
                screen = item
            )
        }
    }
}


@Composable
fun RowScope.AddItem(
    screen: BottomBarIcons
) {

    NavigationBarItem(
        label = {
            Text(text = screen.title)
        },

        // The icon resource
        icon = {
            Icon(
                imageVector = (screen.icon), contentDescription = screen.title
            )
        },

        selected = true,

        alwaysShowLabel = true,

        onClick = { /*TODO*/ },

        colors = NavigationBarItemDefaults.colors()
    )
}
