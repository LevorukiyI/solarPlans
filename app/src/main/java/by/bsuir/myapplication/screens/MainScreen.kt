package by.bsuir.myapplication.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import by.bsuir.myapplication.navigation.Screen
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.DefaultTintColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.bsuir.myapplication.DateDefaults
import by.bsuir.myapplication.MaskVisualTransformation
import by.bsuir.myapplication.Note
import by.bsuir.myapplication.NoteFront.*
import by.bsuir.myapplication.NoteFront
import by.bsuir.myapplication.ui.theme.secondary_color
import by.bsuir.myapplication.ui.theme.text_color
import by.bsuir.vitaliybaranov.myapplication.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun MainScreen(navController: NavController){
//    Button(onClick = {
//        navController.navigate(Screen.AddScreen.withArgs("aaa"))
//    }, colors =  ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colorScheme.background, contentColor = MaterialTheme.colorScheme.primary)){
//        Text(text = "Add")
//    }


    MaterialTheme{
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .background(secondary_color)){
                    IconButton(
                        onClick = {  }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "Назад",
                            modifier = Modifier.size(20.dp),
                            tint = Color.White
                        )

                    }
                }



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


                var text by remember {
                    mutableStateOf("")
                }

                OutlinedTextField(
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        //containerColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = secondary_color,
                        textColor = MaterialTheme.colorScheme.primary,
                    ),

                    value = text,
                    onValueChange = {newText ->
                        text = newText
                    },
                    label = {
                        androidx.compose.material3.Text(text = stringResource(id = R.string.note_label),
                            color = text_color
                        )
                    },
                    maxLines = 7,

                    )

                val list = remember {
                    mutableStateListOf<String>()
                }

                Button(onClick = {
                    //navController.navigate(Screen.AddScreen.withArgs("aaa"))
                    val note: Note = Note()
                    note.date = date
                    note.note = text

                    list.add(note.note)

                    date = ""
                    text = ""

                }, enabled = text != "",
                    colors =  ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.primary,

                        )){

                    Text(
                        text = "Add",
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.primary,
                            //background = MaterialTheme.colorScheme.background,
                        )
                    )
                }
                
                LazyColumn(){
                    itemsIndexed(list){ index, data ->
                        Card(elevation = 8.dp){
                            ListItem(){
                                Box(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = data,
                                        )
                                    IconButton(
                                        onClick =
                                        {
                                            list.removeAt(index)
                                        },

                                        ) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "delete ",
                                            modifier = Modifier.size(20.dp),
                                            tint = Color.Red,

                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                    //#TODO output notes

            }
        }
    }
}