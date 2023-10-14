package com.example.vozdux

import android.app.Application
import com.example.vozdux.data.local.DronesDatabase
import com.example.vozdux.di.DaggerDroneComponent
import com.example.vozdux.di.DroneComponent
import javax.inject.Inject

class DronesMainApplication: Application() {

    lateinit var injector: DroneComponent

    @Inject
    lateinit var local_database: DronesDatabase

    override fun onCreate() {
        super.onCreate()
        injector = DaggerDroneComponent
            .builder()
            .context(applicationContext)
            .build()
        injector.inject(this)
    }
}