package com.example.numbersapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.numbersapp.ui.presentation.FirstScreen
import com.example.numbersapp.ui.presentation.SecondScreen

@Composable
fun Navigation(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Screens.FirstScreen.route) {
        composable(route = Screens.FirstScreen.route) {
            FirstScreen(navController = navController)
        }
        composable(
            route = "${Screens.SecondScreen.route}/{number}/{description}",
            arguments = listOf(
                navArgument("number") {
                    type = NavType.IntType
                },
                navArgument("description") {
                    type = NavType.StringType
                }
            )
        ) { backstack ->
            val number = backstack.arguments?.getInt("number") ?: 0
            val description = backstack.arguments?.getString("description") ?: ""

            SecondScreen(
                navController = navController,
                number = number,
                description = description
            )
        }
    }

}
