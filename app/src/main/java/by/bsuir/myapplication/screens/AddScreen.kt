package by.bsuir.myapplication.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import by.bsuir.myapplication.DateDefaults
import by.bsuir.myapplication.MaskVisualTransformation
import by.bsuir.myapplication.NoteViewModel
import by.bsuir.myapplication.navigation.Screen
import by.bsuir.myapplication.ui.theme.secondary_color
import by.bsuir.myapplication.ui.theme.text_color
import by.bsuir.vitaliybaranov.myapplication.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(navController: NavController,viewModel: NoteViewModel, coroutineScope: CoroutineScope, scaffoldState: ScaffoldState) {

    MaterialTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            )
            {
                Text(
                    text = stringResource(id = R.string.addNoteString),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 28.sp,
                    modifier = Modifier.padding(vertical = 10.dp)
                )

                var date by remember { mutableStateOf("") }

                OutlinedTextField(
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        //containerColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = secondary_color,
                        textColor = MaterialTheme.colorScheme.primary,
                    ),
                    value = date,
                    onValueChange = {newText ->
                        if (newText.length <= DateDefaults.DATE_LENGTH) {
                            date = newText
                        }
                    },
                    visualTransformation = MaskVisualTransformation(DateDefaults.DATE_MASK),
                    label = {
                        androidx.compose.material3.Text(text = stringResource(id = R.string.date_label),
                            color = text_color
                        )
                    },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )


                var goal by remember {
                    mutableStateOf("")
                }

                OutlinedTextField(
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        //containerColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = secondary_color,
                        textColor = MaterialTheme.colorScheme.primary,
                    ),

                    value = goal,
                    onValueChange = { newText ->
                        goal = newText
                    },
                    label = {
                        androidx.compose.material3.Text(
                            text = stringResource(id = R.string.note_label),
                            color = text_color
                        )
                    },
                    maxLines = 7,

                    )

                val list = remember {
                    mutableStateListOf<String>()
                }

                Button(onClick = {
                    viewModel.onClickAddNote(goal, date)
                    coroutineScope.launch {
                        val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                            message = "New note added",
                            actionLabel = "Ok"
                        )
                    }
                    navController.navigate(Screen.MainScreen.route)

                }, enabled = goal != "" && date != "" && date.length == 8,modifier = Modifier.padding(vertical = 16.dp), colors =  ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colorScheme.background, contentColor = MaterialTheme.colorScheme.primary)){
                    Text(text = stringResource(id = R.string.addNote), fontSize = 20.sp, color = MaterialTheme.colorScheme.primary)
                }

            }
        }
    }
}
