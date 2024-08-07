package com.elfak.andjelanikolic.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    data object Map : BottomBarScreen(
        route = "map_screen",
        title = "Map",
        icon = Icons.Filled.LocationOn
    )
    data object Pins : BottomBarScreen(
        route = "pins_screen",
        title = "Pins",
        icon = Icons.Filled.Star
    )
    data object Leaderboard : BottomBarScreen(
        route = "leaderboard_screen",
        title = "Leaderboard",
        icon = Icons.AutoMirrored.Filled.List
    )
    data object Profile : BottomBarScreen(
        route = "profile_screen",
        title = "Profile",
        icon = Icons.Filled.AccountCircle
    )
}