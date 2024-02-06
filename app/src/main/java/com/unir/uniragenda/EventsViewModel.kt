package com.unir.uniragenda

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class EventsViewModel : ViewModel() {
    val events = mutableStateOf(listOf(
        Event(
            eventName = "Entrega de App Agenda",
            eventType = "Cuenta atrás",
            startTime = "05/02/2024 23:00",
            endTime = "05/02/2024 23:59",
            allDay = "No",
            description = "Desarrollo de una aplicación en Android."
        )
    ))
    fun addEvent(event: Event) {
        events.value = events.value + event
    }
}

data class Event(
    var eventName: String,
    var eventType: String,
    var startTime: String,
    var endTime: String,
    var allDay: String,
    var description: String
)