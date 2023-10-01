package by.bsuir.myapplication.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.bsuir.myapplication.ui.theme.secondary_color
import by.bsuir.myapplication.ui.theme.text_color
import by.bsuir.vitaliybaranov.myapplication.R

@Composable
fun AboutScreen(){
    val aboutMeStrings = listOf<Int>(R.string.about1, R.string.about2, R.string.about3);

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Название приложения
            Text(
                text = stringResource(id = R.string.app_name),
                modifier = Modifier.padding(bottom = 8.dp, top = 16.dp),
                fontSize = 30.sp
            )
            // Версия
            Text(
                text = stringResource(id = R.string.version),
                modifier = Modifier.padding(bottom = 8.dp),
                fontSize = 15.sp
            )

            // Лого
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Лого приложения",
                modifier = Modifier
                    .size(250.dp)
                    .padding(vertical = 8.dp)
            )

            // Список преимуществ
            Text(
                text = stringResource(id = R.string.about_us),
                modifier = Modifier.padding(bottom = 13.dp),
                fontSize = 30.sp
            )
            Column {

                for(text: Int in aboutMeStrings) {
                    Text(
                        text = stringResource(id = text),
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .background(secondary_color)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        color = text_color
                    )
                }

            }
            Text(
                text = stringResource(id = R.string.contacts),
                modifier = Modifier.padding( top = 15.dp, bottom = 30.dp),
                textAlign = TextAlign.Center,
                fontSize = 25.sp
            )
            // Ссылки на соцсети
            //#TODO
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {

                val ctx = LocalContext.current

                val url_vk = "https://vk.com"
                val url_git = "https://github.com/LevorukiyI"
                val url_tel = "https://t.me/DontShareMyUsername"
                IconButton(
                    onClick =
                    {
                        val urlIntent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(url_vk)
                        )
                        ctx.startActivity(urlIntent)
                    }
                )
                {
                    Image(
                        painter = painterResource(id = R.drawable.ic_vk),
                        contentDescription = "Our vk: ",
                        modifier = Modifier.size(40.dp)
                    )
                }
                IconButton(
                    onClick =
                    {
                        val urlIntent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(url_tel)
                        )
                        ctx.startActivity(urlIntent)
                    }
                )
                {
                    Image(
                        painter = painterResource(id = R.drawable.ic_tel),
                        contentDescription = "Our telegram: ",
                        modifier = Modifier.size(40.dp)
                    )
                }
                IconButton(
                    onClick =
                    {
                        val urlIntent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(url_git)
                        )
                        ctx.startActivity(urlIntent)
                    }
                )
                {
                    Image(
                        painter = painterResource(id = R.drawable.ic_git),
                        contentDescription = "Our git: ",
                        modifier = Modifier.size(40.dp)
                    )
                }


            }
        }
    }

}