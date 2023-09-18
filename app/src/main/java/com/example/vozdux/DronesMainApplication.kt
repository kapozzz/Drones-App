package com.example.vozdux

import android.app.Application
import com.example.vozdux.di.DaggerDroneComponent
import com.example.vozdux.di.DroneComponent

class DronesMainApplication: Application() {

    lateinit var injector: DroneComponent

    override fun onCreate() {
        super.onCreate()
        injector = DaggerDroneComponent.create()
    }
}