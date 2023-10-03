package by.bsuir.myapplication

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import by.bsuir.myapplication.ui.theme.MyApplicationTheme
import androidx.activity.compose.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import by.bsuir.myapplication.navigation.Navigation
import by.bsuir.myapplication.navigation.Screen
import by.bsuir.myapplication.ui.theme.background_color
import android.R
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color





class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

           MyApplicationTheme {

                val bottomItems = listOf(Screen.MainScreen, Screen.WeatherScreen, Screen.About)
                val navController = rememberNavController();

                Surface(
                    color = MaterialTheme.colorScheme.surface
                ) {
                    Scaffold(
                        bottomBar = {
                            BottomNavigation(backgroundColor = MaterialTheme.colorScheme.background) {
                                bottomItems.forEach { screen ->
                                    BottomNavigationItem(
                                        selected = false,
                                        onClick = {
                                            navController.navigate(screen.route) {
                                                launchSingleTop = true
                                            }
                                        },
                                        label = { Text(stringResource(id = screen.titleResourceId),color = MaterialTheme.colorScheme.tertiary) },
                                        icon = {

                                        })
                                }
                            }
                        }, backgroundColor = MaterialTheme.colorScheme.surface
                    ) {
                        Navigation(navController = navController)
                    }
                }
            }
        }
    }


    private val permissionsToRequest = arrayOf(
        Manifest.permission.INTERNET,
        Manifest.permission.CHANGE_NETWORK_STATE,
        Manifest.permission.CHANGE_WIFI_STATE,
        Manifest.permission.POST_NOTIFICATIONS,
        Manifest.permission.SCHEDULE_EXACT_ALARM,
        Manifest.permission.FOREGROUND_SERVICE,
        Manifest.permission.RECEIVE_BOOT_COMPLETED,
        Manifest.permission.CAMERA,
    )
}
