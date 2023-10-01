package by.bsuir.myapplication.screens

import androidx.compose.foundation.background
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import by.bsuir.myapplication.navigation.Screen
import androidx.compose.material3.MaterialTheme
@Composable
fun MainScreen(navController: NavController){
    Button(onClick = {
        navController.navigate(Screen.AddScreen.withArgs("aaa"))
    }, colors =  ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colorScheme.background, contentColor = MaterialTheme.colorScheme.primary)){
        Text(text = "Add")
    }
}