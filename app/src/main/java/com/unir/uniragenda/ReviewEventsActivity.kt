package com.unir.uniragenda

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import android.os.Bundle
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.unir.uniragenda.ui.theme.UniragendaTheme

class ReviewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = (application as GlobalApp).eventsViewModel
        setContent {
            UniragendaTheme {
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ){
                    ShowEvents(viewModel)
                    Spacer(modifier = Modifier.height(16.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Button(onClick = { finish() },
                            colors = ButtonDefaults.buttonColors(contentColor = Color(0xFFfffedc))
                        ) {
                            Text("Regresar")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ShowEvents(viewModel: EventsViewModel) {
    val events = viewModel.events.value
    for (event in events) {
        Text(text = "Nombre: ${event.eventName}")
        Text(text = "Tipo: ${event.eventType}")
        Text(text = "Inicio: ${event.startTime}")
        Text(text = "Fin: ${event.endTime}")
        Text(text = "Todo el día: ${event.allDay}")
        Text(text = "Descripción: ${event.description}")
        Divider()
    }
}