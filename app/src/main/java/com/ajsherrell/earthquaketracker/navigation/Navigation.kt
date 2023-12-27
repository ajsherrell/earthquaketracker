package com.ajsherrell.earthquaketracker.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ajsherrell.earthquaketracker.presentation.QuakeViewModel
import com.ajsherrell.earthquaketracker.screens.MainScreen
import com.ajsherrell.earthquaketracker.screens.CountScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(viewModel: QuakeViewModel) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route
    ) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController = navController, viewModel = viewModel)
        }
        composable(
            route = Screen.CountScreen.route + "/{startTime}/{endTime}/{minMagnitude}",
            arguments = listOf(
                navArgument(name = "startTime") {
                    type = NavType.StringType
                },
                navArgument(name = "endTime") {
                    type = NavType.StringType
                },
                navArgument(name = "minMagnitude") {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            val start = entry.arguments?.getString("startTime")!!
            val end = entry.arguments?.getString("endTime")!!
            val mag = entry.arguments?.getString("minMagnitude")!!
            CountScreen(
                modifier = Modifier,
                navController = navController,
                viewModel = viewModel,
                startTime = start,
                endTime = end,
                minMagnitude = mag
            )
        }
    }
}
