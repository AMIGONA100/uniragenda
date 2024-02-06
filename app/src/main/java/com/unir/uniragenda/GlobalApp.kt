package com.unir.uniragenda

import android.app.Application

class GlobalApp : Application() {
    lateinit var eventsViewModel: EventsViewModel

    override fun onCreate() {
        super.onCreate()
        eventsViewModel = EventsViewModel()
    }
}