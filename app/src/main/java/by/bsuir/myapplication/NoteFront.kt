package by.bsuir.myapplication

import android.service.autofill.DateTransformation
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.bsuir.myapplication.ui.theme.secondary_color
import by.bsuir.myapplication.ui.theme.text_color
import by.bsuir.vitaliybaranov.myapplication.R

class NoteFront {

    companion object {
        @OptIn(ExperimentalMaterial3Api::class)
        @Composable
        fun MakeNotesScreen() : Note {
            val note: Note = Note()

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

                    // Название приложения
                    Text(
                        text = stringResource(id = R.string.app_name),
                        modifier = Modifier.padding(bottom = 8.dp, top = 16.dp),
                        fontSize = 30.sp,
                        color = text_color
                    )
                    // Версия
                    Text(
                        text = stringResource(id = R.string.version),
                        modifier = Modifier.padding(bottom = 8.dp),
                        fontSize = 15.sp,
                        color = text_color
                    )

                    // Лого
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = "Лого приложения",
                        modifier = Modifier
                            .size(250.dp)
                            .padding(vertical = 8.dp)
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
                            Text(text = stringResource(id = R.string.note_label),
                                color = text_color)
                        },
                        maxLines = 7,

                    )
                    note.note = text

                    var date by remember { mutableStateOf("")}


                }
            }

            return note
        }
    }
}

