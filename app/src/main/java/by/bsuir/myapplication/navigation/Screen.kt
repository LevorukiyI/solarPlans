package by.bsuir.myapplication.navigation

import by.bsuir.vitaliybaranov.myapplication.R

sealed class Screen(val route: String, val  titleResourceId: Int){
    object MainScreen: Screen("main_screen", R.string.title_main)
    object WeatherScreen: Screen("weather_screen", R.string.title_weather)
    object AddScreen: Screen("add_screen", -1)
    object About: Screen("about_screen", R.string.title_about)

    fun withArgs(vararg args: String) : String{
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
