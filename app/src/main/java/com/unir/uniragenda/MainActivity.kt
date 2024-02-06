package com.unir.uniragenda

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Alignment
import com.unir.uniragenda.ui.theme.UniragendaTheme
import android.content.Intent
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UniragendaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp(this)
                }
            }
        }
    }
}

@Composable
fun MyApp(activity: ComponentActivity) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val image: Painter = painterResource(id = R.drawable.ima_agenda)
        Image(painter = image, contentDescription = "App image")
        Spacer(modifier = Modifier.height(10.dp))
        Greeting("Horacio Ramírez")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val intent = Intent(activity, ReviewActivity::class.java)
            activity.startActivity(intent)
        }, colors = ButtonDefaults.buttonColors(contentColor = Color(0xFFfffedc))
        ) {
            Text("Revisar eventos")
        }
        Button(onClick = {
            val intent = Intent(activity, AddEventActivity::class.java)
            activity.startActivity(intent)
            }, colors = ButtonDefaults.buttonColors(contentColor = Color(0xFFfffedc))
            ) {
            Text("Agregar evento")
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hola $name!",
        color = Color(0xFFfffedc)
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    UniragendaTheme {
        Greeting("Horacio Ramírez")
    }
}