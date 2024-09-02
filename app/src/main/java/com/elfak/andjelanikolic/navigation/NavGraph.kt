package com.elfak.andjelanikolic.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.elfak.andjelanikolic.screens.create.CreateScreen
import com.elfak.andjelanikolic.screens.filter.FilterScreen
import com.elfak.andjelanikolic.screens.filter.FilterViewModel
import com.elfak.andjelanikolic.screens.home.HomeScreen
import com.elfak.andjelanikolic.screens.login.LoginScreen
import com.elfak.andjelanikolic.screens.register.RegisterScreen


@Composable
fun NavGraph(controller: NavHostController, start: String) {
    val filterViewModel = viewModel<FilterViewModel>()

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
            HomeScreen(controller = controller, filterViewModel = filterViewModel)
        }
        composable(route = "create_screen/{latitude}/{longitude}") {
            val latitude = it.arguments?.getString("latitude")?.toFloatOrNull()
            val longitude = it.arguments?.getString("longitude")?.toFloatOrNull()
            CreateScreen(controller = controller, latitude = latitude, longitude = longitude)
        }
        composable(route = "filter_screen") {
            FilterScreen(controller = controller, filterViewModel = filterViewModel)
        }
    }
}