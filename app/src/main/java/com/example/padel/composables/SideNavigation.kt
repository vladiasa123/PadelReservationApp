package com.example.padel.composables

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.padel.data.BottomBarIcons

@Composable
fun SideNavigation() {
    val items = listOf(
        BottomBarIcons.Home, BottomBarIcons.List, BottomBarIcons.Profile
    )

    NavigationRail {
        items.forEach { item ->
            AddRailItem(
                screen = item,
            )
        }
    }
}

@Composable
fun ColumnScope.AddRailItem(
    screen: BottomBarIcons
) {
    NavigationRailItem(
        modifier = Modifier.padding(15.dp),
        label = {
            Text(text = screen.title)
        },


        icon = {
            Icon(
                imageVector = screen.icon, contentDescription = screen.title
            )
        },

        selected = true,

        alwaysShowLabel = true,

        onClick = { /*TODO*/ },

        colors = NavigationRailItemDefaults.colors()
    )
}
