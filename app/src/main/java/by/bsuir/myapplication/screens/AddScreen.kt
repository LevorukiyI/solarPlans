package by.bsuir.myapplication.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun AddScreen(name: String?){
    Text(text = "Wow, man")
    Text(text = name.toString())
}