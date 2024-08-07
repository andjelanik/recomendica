package com.elfak.andjelanikolic.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.elfak.andjelanikolic.screens.leaderboard.LeaderboardScreen
import com.elfak.andjelanikolic.screens.map.MapScreen
import com.elfak.andjelanikolic.screens.pins.PinsScreen
import com.elfak.andjelanikolic.screens.profile.ProfileScreen

@Composable
fun BottomBarGraph(controller: NavHostController) {
    NavHost(
        navController = controller,
        startDestination = BottomBarScreen.Map.route
    ) {
        composable(route = BottomBarScreen.Map.route) {
            MapScreen(controller = controller)
        }
        composable(route = BottomBarScreen.Pins.route) {
            PinsScreen(controller = controller)
        }
        composable(route = BottomBarScreen.Leaderboard.route) {
            LeaderboardScreen(controller = controller)
        }
        composable(route = BottomBarScreen.Profile.route) {
            ProfileScreen(controller = controller)
        }
    }
}