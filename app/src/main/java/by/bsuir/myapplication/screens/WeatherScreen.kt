package by.bsuir.myapplication.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.bsuir.myapplication.data.WeatherModel
import by.bsuir.vitaliybaranov.myapplication.R

@Preview(showBackground = true)
@Composable
fun WeatherScreen() {
    MaterialTheme {
        Image(
            painter = painterResource(id = R.drawable.clouds),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = MaterialTheme.colorScheme.surface,
                elevation = 0.dp,
                shape = RoundedCornerShape(10.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier.padding(top = 8.dp, start = 8.dp),
                            text = "20 Jun 2023",
                            fontSize = 20.sp, color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            modifier = Modifier.padding(top = 8.dp, end = 8.dp),
                            text = "Nice day",
                            fontSize = 20.sp, color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = "Minsk",
                        fontSize = 30.sp, color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = "0°C",
                        fontSize = 50.sp, color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                        text = "Sunny",
                        fontSize = 20.sp, color = MaterialTheme.colorScheme.primary
                    )

                }
            }
            val items = listOf(WeatherModel("Minsk", "sd", "df", "sdf", "sdf", "df"))
            if(items.size!=0){
                LazyColumn(modifier = Modifier.padding(bottom = 50.dp)){

                    itemsIndexed(items = items) { index, item ->
                        Item(item = item, index = index)
                    }
                }
            }
            else{
                Text(text = "Упс... Ошибочка",
                    modifier = Modifier.padding(top = 8.dp, start = 8.dp, bottom=15.dp),
                    fontSize = 60.sp, color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@Composable
fun Item(item: WeatherModel, index: Int){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 3.dp), backgroundColor = MaterialTheme.colorScheme.surface,
        elevation = 0.dp,
        shape = RoundedCornerShape(5.dp)
    ){
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically){
            Column {
                Text(text = item.time,
                    modifier = Modifier.padding(top = 8.dp, start = 8.dp),
                    fontSize = 20.sp, color = MaterialTheme.colorScheme.primary)
                Text(text = item.condition,
                    modifier = Modifier.padding(top = 8.dp, start = 8.dp),
                    fontSize = 15.sp, color = MaterialTheme.colorScheme.primary)
            }
            Text(text = "${item.minTemp}°C/${item.maxTemp}°C",
                modifier = Modifier.padding(top = 8.dp, start = 8.dp, bottom=15.dp),
                fontSize = 30.sp, color = MaterialTheme.colorScheme.primary)
            IconButton(
                onClick =
                {

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
        }
    }
}
