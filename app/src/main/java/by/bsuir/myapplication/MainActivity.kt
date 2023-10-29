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
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.CoroutineScope


class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

           MyApplicationTheme {

               val bottomItems = listOf(Screen.MainScreen, Screen.WeatherScreen, Screen.About)
               val navController = rememberNavController()

               val scaffoldState: ScaffoldState = rememberScaffoldState()
               val coroutineScope: CoroutineScope = rememberCoroutineScope()

                Surface(
                    color = MaterialTheme.colorScheme.surface
                ) {
                    Scaffold(scaffoldState = scaffoldState,
                        bottomBar = {
                            BottomNavigation(backgroundColor = MaterialTheme.colorScheme.background) {
                                val navBackStackEntry by navController.currentBackStackEntryAsState()
                                val currentDestination = navBackStackEntry?.destination

                                bottomItems.forEach { screen ->
                                    BottomNavigationItem(
                                        selected = currentDestination?.hierarchy?.any{it.route == screen.route} == true,//это писал индус
                                        onClick = {
                                            navController.navigate(screen.route) {
                                            popUpTo(navController.graph.findStartDestination().id){
                                                saveState = true
                                            }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        },
                                        label = { Text(stringResource(id = screen.titleResourceId),color = MaterialTheme.colorScheme.tertiary) },
                                        icon = {

                                        })
                                }
                            }
                        }, backgroundColor = MaterialTheme.colorScheme.surface
                    ) {
                        Navigation(navController = navController, coroutineScope = coroutineScope, scaffoldState = scaffoldState)
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
