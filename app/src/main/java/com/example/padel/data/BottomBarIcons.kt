package com.example.padel.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarIcons(
    var title: String, var icon: ImageVector, var route: String
) {

    object Home : BottomBarIcons("Home", Icons.Filled.Home, "screenB")
    object Profile : BottomBarIcons("Profile", Icons.Filled.AccountCircle, "screenC")
    object Booking : BottomBarIcons("Booking", Icons.Filled.DateRange, "screenB")
    object MyReservations :
        BottomBarIcons("Reservations", Icons.Filled.List, "screenD")
}

