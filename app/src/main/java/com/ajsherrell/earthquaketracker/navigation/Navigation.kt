package com.ajsherrell.earthquaketracker.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ajsherrell.earthquaketracker.CountScreen
import com.ajsherrell.earthquaketracker.MainScreen
import com.ajsherrell.earthquaketracker.presentation.QuakeViewModel

@Composable
fun Navigation(viewModel: QuakeViewModel) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route
    ) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController = navController)
        }
        composable(
            route = Screen.CountScreen.route + "/{startTime}/{endTime}",
            arguments = listOf(
                navArgument(name = "startTime") {
                    type = NavType.StringType
                },
                navArgument(name = "endTime") {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            val start = entry.arguments?.getString("startTime")!!
            val end = entry.arguments?.getString("endTime")!!
            CountScreen(
                modifier = Modifier,
                navController = navController,
                viewModel = viewModel,
                startTime = start,
                endTime = end
            )
        }
    }
}