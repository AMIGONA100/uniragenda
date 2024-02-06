package com.unir.uniragenda

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.unir.uniragenda.ui.theme.UniragendaTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.setValue
import java.util.Calendar
import java.text.SimpleDateFormat
import android.app.DatePickerDialog
import androidx.compose.ui.platform.LocalContext
import android.app.TimePickerDialog
import android.widget.Toast
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.mutableIntStateOf
import java.util.Locale

class AddEventActivity : ComponentActivity() {
    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = (application as GlobalApp).eventsViewModel
        setContent {
            UniragendaTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val eventNameState = remember { mutableStateOf("") }
                    val eventTypeState = remember { mutableStateOf("") }
                    val timeState = remember { mutableStateOf("") }
                    val startTimeState = remember { mutableStateOf("") }
                    val endTimeState = remember { mutableStateOf("") }
                    val allDayState = remember { mutableStateOf("No") }
                    val descriptionState = remember { mutableStateOf("") }
                    val containerColor = Color.DarkGray
                    val isDatePickerDialogShowing = remember { mutableStateOf(false) }
                    val isTimePickerDialogShowing = remember { mutableStateOf(false) }
                    val calendar = Calendar.getInstance()
                    val calendarButton = remember { mutableStateOf(false) }
                    val validateData = remember { mutableIntStateOf(0) }

                    Text(
                        text = "Agregar Evento",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color(0xFFfffedc),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = eventNameState.value,
                        onValueChange = { eventNameState.value = it },
                        label = { Text("Nombre del evento") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = containerColor,
                            unfocusedContainerColor = containerColor,
                            disabledContainerColor = containerColor,
                        )
                    )

                    eventTypeState.value = myExposedDropdownMenu()

                    OutlinedTextField(
                        value = startTimeState.value,
                        onValueChange = { startTimeState.value = it },
                        label = { Text("Fecha inicio") },
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = true,
                        trailingIcon = {
                            Row {
                                IconButton(onClick = {
                                    isDatePickerDialogShowing.value = true
                                    calendarButton.value = true }) {
                                    Icon(Icons.Filled.DateRange, contentDescription = "Seleccionar fecha")
                                }
                            }
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = containerColor,
                            unfocusedContainerColor = containerColor,
                            disabledContainerColor = containerColor,
                        )
                    )

                    OutlinedTextField(
                        value = endTimeState.value,
                        onValueChange = { endTimeState.value = it },
                        label = { Text("Fecha fin") },
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = true,
                        trailingIcon = {
                            Row {
                                IconButton(onClick = {
                                    isDatePickerDialogShowing.value = true
                                    calendarButton.value = false }) {
                                    Icon(Icons.Filled.DateRange, contentDescription = "Seleccionar fecha")
                                }
                            }
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = containerColor,
                            unfocusedContainerColor = containerColor,
                            disabledContainerColor = containerColor,
                        )
                    )

                    if (isDatePickerDialogShowing.value) {
                        DatePickerDialog(
                            LocalContext.current,
                            { _, year, month, dayOfMonth ->
                                calendar.set(year, month, dayOfMonth)
                                timeState.value = SimpleDateFormat("dd/MM/yyyy").format(calendar.time)
                                isDatePickerDialogShowing.value = false
                                isTimePickerDialogShowing.value = true
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                        ).show()
                    }

                    if (isTimePickerDialogShowing.value) {
                        TimePickerDialog(
                            LocalContext.current,
                            { _, hourOfDay, minute ->
                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                                calendar.set(Calendar.MINUTE, minute)
                                if (calendarButton.value)
                                {
                                    startTimeState.value = timeState.value + " " + SimpleDateFormat("HH:mm").format(calendar.time)
                                }
                                else
                                {
                                    endTimeState.value = timeState.value + " " + SimpleDateFormat("HH:mm").format(calendar.time)
                                }
                                isDatePickerDialogShowing.value = false
                                isTimePickerDialogShowing.value = false
                                calendarButton.value = false
                            },
                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE),
                            true
                        ).show()
                    }


                    if (mySwitch())
                    {
                        allDayState.value = "Sí"
                        if(startTimeState.value.isEmpty())
                        {
                            val date = Calendar.getInstance().time
                            val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                            timeState.value = formatter.format(date)
                        }
                        startTimeState.value = timeState.value + " 00:00"
                        endTimeState.value = timeState.value + " 23:59"
                    }

                    OutlinedTextField(
                        value = descriptionState.value,
                        onValueChange = { descriptionState.value = it },
                        label = { Text("Descripción") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = containerColor,
                            unfocusedContainerColor = containerColor,
                            disabledContainerColor = containerColor,
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(onClick = {
                            validateData.intValue = validateData(eventNameState.value, startTimeState.value, endTimeState.value)

                            if (validateData.intValue == 1) {

                                Toast.makeText(this@AddEventActivity, "EL nombre de evento no puede estar vacío", Toast.LENGTH_SHORT).show()
                            }

                            if (validateData.intValue == 2) {

                                Toast.makeText(this@AddEventActivity, "La fecha inicial no puede estar vacia", Toast.LENGTH_SHORT).show()
                            }

                            if (validateData.intValue == 3) {

                                Toast.makeText(this@AddEventActivity, "La fecha fin no puede estar vacia", Toast.LENGTH_SHORT).show()
                            }

                            if (validateData.intValue == 4) {

                                Toast.makeText(this@AddEventActivity, "La fecha de inicio debe ser anterior a la fecha de fin", Toast.LENGTH_SHORT).show()
                            }

                            if (validateData.intValue == 0) {
                                val newEvent = Event(
                                    eventName = eventNameState.value,
                                    eventType = eventTypeState.value,
                                    startTime = startTimeState.value,
                                    endTime = endTimeState.value,
                                    allDay = allDayState.value,
                                    description = descriptionState.value
                                )
                                viewModel.addEvent(newEvent)
                                finish()
                            }

                           },
                            colors = ButtonDefaults.buttonColors(contentColor = Color(0xFFfffedc))
                        ) {
                            Text("Confirmar")
                        }

                        Button(onClick = { finish() },
                            colors = ButtonDefaults.buttonColors(contentColor = Color(0xFFfffedc))
                        ) {
                            Text("Cancelar")
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun myExposedDropdownMenu() : String{
    val isEventType = remember { mutableStateOf(false) }
    val options = listOf("Cita", "Aniversario", "Cuenta atrás")
    var selectedOptionText by remember { mutableStateOf(options[0]) }
    val containerColor = Color.DarkGray

    Box(modifier = Modifier.fillMaxWidth()) {
        ExposedDropdownMenuBox(
            expanded = isEventType.value,
            onExpandedChange = { isEventType.value = it },
        ) {
            OutlinedTextField(
                modifier = Modifier.menuAnchor(),
                readOnly = true,
                value = selectedOptionText,
                onValueChange = {},
                label = { Text("Tipo de evento: ") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isEventType.value) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = containerColor,
                    unfocusedContainerColor = containerColor,
                    disabledContainerColor = containerColor,
                )
            )
            ExposedDropdownMenu(
                expanded = isEventType.value,
                onDismissRequest = { isEventType.value = false },
            ) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            selectedOptionText = selectionOption
                            isEventType.value = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }
    }
    return selectedOptionText
}

@Composable
fun mySwitch() : Boolean {
    var checkedState by remember { mutableStateOf(false) }
    val containerColor = Color.DarkGray
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Todo el día")

        Spacer(modifier = Modifier.weight(1f))

        Switch(
            checked = checkedState,
            onCheckedChange = { checkedState = it },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = containerColor,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = containerColor
            )
        )
    }
    return checkedState
}

@SuppressLint("SimpleDateFormat")
fun validateData(name: String, startTime: String, endTime: String): Int {
    val format = SimpleDateFormat("dd/MM/yyyy HH:mm")

    if(name.isEmpty())
    {
        return 1
    }

    if(startTime.isEmpty())
    {
        return 2
    }
    val startDate = format.parse(startTime)

    if(endTime.isEmpty())
    {
        return 3
    }
    val endDate = format.parse(endTime)

    if(startDate != null && endDate != null && startDate >= endDate)
    {
        return 4
    }

    return 0
}