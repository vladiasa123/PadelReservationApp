package com.example.padel.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarIcons(
    var title: String, var icon: ImageVector
) {

    object Home : BottomBarIcons(
        "Home", Icons.Filled.Home
    )

    object List :  BottomBarIcons(
        "List", Icons.Filled.AccountCircle
    )

    object Profile :  BottomBarIcons(
        "Analytics", Icons.Filled.DateRange
    )

}
