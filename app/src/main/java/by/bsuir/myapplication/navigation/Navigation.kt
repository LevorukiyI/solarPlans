package by.bsuir.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import by.bsuir.myapplication.screens.AboutScreen
import by.bsuir.myapplication.screens.AddScreen
import by.bsuir.myapplication.screens.MainScreen
import by.bsuir.myapplication.screens.WeatherScreen

@Composable
fun Navigation(navController: NavController){
    NavHost(
        navController = navController as NavHostController,
        startDestination = Screen.MainScreen.route
    ) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController = navController)
        }
        composable(
            route = Screen.AddScreen.route + "/{name}", arguments = listOf(
                navArgument("name") {
                    type = NavType.StringType
                    defaultValue = "Vova"
                    nullable = true
                })
        ) { entry ->
            AddScreen(name = entry.arguments?.getString("name"))
        }
        composable(route = Screen.About.route) {
            AboutScreen()
        }
        composable(route = Screen.WeatherScreen.route) {
            WeatherScreen()
        }
    }
}


