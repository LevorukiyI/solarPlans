package by.bsuir.myapplication.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import by.bsuir.myapplication.NoteViewModel
import by.bsuir.myapplication.Notes
import by.bsuir.myapplication.navigation.Screen
import by.bsuir.vitaliybaranov.myapplication.R

@Composable
fun HomeScreen(navController: NavController, viewModel: NoteViewModel){

    MaterialTheme {
        Column(horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier.fillMaxSize()){
                Button(onClick = {
                    navController.navigate(Screen.AddScreen.route)
                }, modifier = Modifier.padding(vertical = 16.dp), colors =  ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colorScheme.background, contentColor = MaterialTheme.colorScheme.primary)){
                    Text(text = stringResource(id = R.string.addNote), fontSize = 20.sp, color = MaterialTheme.colorScheme.primary)
                }
            HomeScreenContent(
                items = viewModel.items,
                onEdit = {navController.navigate("add_screen")},
                onRemove = viewModel::onClickRemoveNote,
                navController = navController
            )
        }

    }
}

@Composable
private fun HomeScreenContent(
    items: SnapshotStateList<Notes>,
    onRemove: (Notes) -> Unit,
    onEdit: () -> Unit,
    navController: NavController
) {
    if(items.size!=0){
        LazyColumn(modifier = Modifier.padding(bottom = 50.dp)){

            itemsIndexed(items = items) { index, note ->
                NoteItem(note = note, onRemove = onRemove, navController = navController)
            }
        }
    }
    else{
        Image(
            painter = painterResource(id = R.drawable.man),
            contentDescription = "Лого приложения",
            modifier = Modifier
                .size(400.dp)
                .padding(vertical = 8.dp),
        )
    }

}

@Composable
private fun NoteItem(
    note: Notes,
    onRemove: (Notes) -> Unit,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 6.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.goal,),
                    modifier = Modifier
                        .padding(vertical = 1.dp, horizontal = 5.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 28.sp, color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = note.goal,
                    modifier = Modifier
                        .padding(vertical = 2.dp, horizontal = 5.dp),
                    textAlign = TextAlign.Start,
                    fontSize = 20.sp, color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = note.date,
                    modifier = Modifier
                        .padding(vertical = 3.dp, horizontal = 5.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp, color = MaterialTheme.colorScheme.primary
                )
                Text(text = stringResource(id = R.string.temperature) + note.weather.temperature + stringResource(id =R.string.humidity) + note.weather.humidity + stringResource(id = R.string.raininess) + note.weather.raininess + stringResource(id = R.string.cloudCover) + note.weather.cloudCover,
                    modifier = Modifier
                        .padding(vertical = 3.dp, horizontal = 5.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
            }
            }
            Row {
                IconButton(
                    onClick =
                    {
                        navController.navigate(Screen.AddScreen.route)
                    }
                )
                {
                    Image(
                        painter = painterResource(id = R.drawable.edit_icon),
                        contentDescription = "",
                        modifier = Modifier
                            .size(40.dp)
                            .padding(vertical = 6.dp, horizontal = 3.dp)
                    )
                }

                IconButton(
                    onClick =
                    {
                        onRemove(note)
                    }
                )
                {
                    Image(
                        painter = painterResource(id = R.drawable.basket_icon),
                        contentDescription = "",
                        modifier = Modifier
                            .size(40.dp)
                            .padding(vertical = 6.dp, horizontal = 3.dp)
                    )
                }


            }
        }

}