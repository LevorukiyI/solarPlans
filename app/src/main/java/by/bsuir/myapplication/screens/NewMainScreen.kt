package by.bsuir.myapplication.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed

import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import by.bsuir.myapplication.database.entity.Notes
import by.bsuir.myapplication.navigation.Screen
import by.bsuir.vitaliybaranov.myapplication.R
import androidx.lifecycle.compose.collectAsStateWithLifecycle

import by.bsuir.myapplication.HomeViewModel

@Composable
fun HomeScreen(navController: NavController){
    val viewModel: HomeViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()


    MaterialTheme {
        Column(horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier.fillMaxSize()){
                Button(onClick = {
                    navController.navigate(Screen.AddScreen.route)
                }, modifier = Modifier.padding(vertical = 16.dp), colors =  ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colorScheme.background, contentColor = MaterialTheme.colorScheme.primary)){
                    Text(text = stringResource(id = R.string.addNote), fontSize = 20.sp, color = MaterialTheme.colorScheme.primary)
                }
            HomeScreenContent(
                items = uiState.notes,
                onEdit = { it -> navController.navigate(Screen.EditScreen.withArgs(it.id.toString()))},
                onRemove = {
                           viewModel.deleteNote(it.id)
                },
                navController = navController
            )
        }

    }
}

@Composable
private fun HomeScreenContent(
    items: List<Notes>,
    onRemove: (Notes) -> Unit,
    onEdit: (Notes) -> Unit,
    navController: NavController
) {
    if(items.size!=0){
        LazyColumn(modifier = Modifier.padding(bottom = 50.dp)){

            itemsIndexed(items = items) { index, note ->
                NoteItem(note = note, onRemove = onRemove, onEdit= onEdit, navController = navController, index = index)
            }
        }
    }
    else{
        Image(
            painter = painterResource(id = R.drawable.man),
            contentDescription = "",
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
    onEdit: (Notes) -> Unit,
    modifier: Modifier = Modifier,
    navController: NavController,
    index: Int
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
                    text = note.date.substring(0,2)+ "." + note.date.substring(2,4)+ "." + note.date.substring(4,8),
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
                        onEdit(note)
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