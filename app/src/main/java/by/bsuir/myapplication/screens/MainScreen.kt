package by.bsuir.myapplication.screens

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import by.bsuir.myapplication.navigation.Screen

@Composable
fun MainScreen(navController: NavController){
    Button(onClick = {
        navController.navigate(Screen.AddScreen.withArgs("aaa"))
    }){
        Text(text = "Add")
    }
}