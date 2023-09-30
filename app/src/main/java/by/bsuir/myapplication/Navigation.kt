package by.bsuir.myapplication

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun Navigation(){
    val navController = rememberNavController();
    NavHost(navController = navController, startDestination = Screen.MainScreen.route){
        composable(route = Screen.MainScreen.route){
            MainScreen(navController = navController)
        }
        composable(route = Screen.AddScreen.route + "/{name}", arguments = listOf(navArgument("name"){
            type = NavType.StringType
            defaultValue =  "Vova"
            nullable = true
        })){
            entry -> AddScreen(name = entry.arguments?.getString("name"))
        }
    }
}

@Composable
fun MainScreen(navController: NavController){
    Button(onClick = {
        navController.navigate(Screen.AddScreen.withArgs("aaa"))
    }){
        Text(text = "Click")
    }
}

@Composable
fun AddScreen(name: String?){
    Text(text = "Wow, man")
    Text(text = name.toString())
}