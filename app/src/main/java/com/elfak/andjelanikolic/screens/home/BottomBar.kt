package com.elfak.andjelanikolic.screens.home

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.elfak.andjelanikolic.navigation.BottomBarScreen
import com.elfak.andjelanikolic.ui.theme.backgroundDarker
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import com.elfak.andjelanikolic.ui.theme.primary
import com.elfak.andjelanikolic.ui.theme.tertiary

@Composable
fun BottomBar(controller: NavController) {
    val screens = listOf(
        BottomBarScreen.Map,
        BottomBarScreen.Pins,
        BottomBarScreen.Leaderboard,
        BottomBarScreen.Profile
    )
    val navBackStackEntry by controller.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        containerColor = backgroundDarker
    ) {
        screens.forEach { screen ->
            AddItem(screen = screen, currentDestination = currentDestination, controller = controller)
        }
    }
}

@Composable
fun RowScope.AddItem(screen: BottomBarScreen, currentDestination: NavDestination?, controller: NavController) {
    NavigationBarItem(
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        label = {
            Text(text = screen.title)
        },
        icon = {
            Icon(imageVector = screen.icon, contentDescription = "Navigation Icon")
        },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = tertiary,
            unselectedIconColor = tertiary.copy(alpha = 0.6f),
            selectedTextColor = tertiary,
            unselectedTextColor = tertiary.copy(alpha = 0.6f),
            indicatorColor = primary
        ),
        onClick = {
            if (currentDestination?.route != screen.route) {
                controller.navigate(screen.route) {
                    popUpTo(controller.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        },
    )
}
