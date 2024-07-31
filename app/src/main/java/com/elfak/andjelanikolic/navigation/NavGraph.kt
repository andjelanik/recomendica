package com.elfak.andjelanikolic.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.elfak.andjelanikolic.screens.home.HomeScreen
import com.elfak.andjelanikolic.screens.login.LoginScreen
import com.elfak.andjelanikolic.screens.register.RegisterScreen


@Composable
fun NavGraph(controller: NavHostController, start: String) {
    NavHost(
        navController = controller,
        startDestination = start
    ) {
        composable(route = "register_screen") {
            RegisterScreen(controller = controller)
        }
        composable(route = "login_screen") {
            LoginScreen(controller = controller)
        }
        composable(route = "home_screen") {
            HomeScreen(controller = controller)
        }
    }
}