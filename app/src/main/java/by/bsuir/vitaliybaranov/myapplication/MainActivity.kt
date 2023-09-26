package by.bsuir.vitaliybaranov.myapplication

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import by.bsuir.vitaliybaranov.myapplication.ui.theme.MyApplicationTheme
import com.plcoding.permissionsguidecompose.MainViewModel
import by.bsuir.vitaliybaranov.myapplication.ui.theme.PermissionDialog.*
import androidx.activity.compose.*
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.contentColorFor
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.startActivity
import android.content.res.Resources
import by.bsuir.vitaliybaranov.myapplication.ui.*
import by.bsuir.vitaliybaranov.myapplication.ui.theme.Purple80
import by.bsuir.vitaliybaranov.myapplication.ui.theme.background_color
import by.bsuir.vitaliybaranov.myapplication.ui.theme.secondary_color
import by.bsuir.vitaliybaranov.myapplication.ui.theme.text_color

class MainActivity : ComponentActivity() {

    companion object{
        val aboutMeStrings = listOf<Int>(R.string.about1, R.string.about2, R.string.about3);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {


            val viewModel = viewModel<MainViewModel>()
            val dialogQueue = viewModel.visiblePermissionDialogQueue

            MyApplicationTheme {

                val viewModel = viewModel<MainViewModel>()
                val dialogQueue = viewModel.visiblePermissionDialogQueue



                val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestMultiplePermissions(),
                    onResult = { perms ->
                        permissionsToRequest.forEach { permission ->
                            viewModel.onPermissionResult(
                                permission = permission,
                                isGranted = perms[permission] == true
                            )
                        }
                    }
                )

                //multiplePermissionResultLauncher.launch(permissionsToRequest)
                // A surface container using the 'background' color from the theme

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = background_color

                ) {


                    AboutScreen()


                }
            }
        }
    }

    @Composable
    fun AboutScreen() {

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

                    for(text: Int in MainActivity.aboutMeStrings) {
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

    private val permissionsToRequest = arrayOf(
        Manifest.permission.INTERNET,
        Manifest.permission.CHANGE_NETWORK_STATE,
        Manifest.permission.CHANGE_WIFI_STATE,
        Manifest.permission.POST_NOTIFICATIONS,
        Manifest.permission.SCHEDULE_EXACT_ALARM,
        Manifest.permission.FOREGROUND_SERVICE,
        Manifest.permission.RECEIVE_BOOT_COMPLETED,
        Manifest.permission.CAMERA,
    )



}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )

}