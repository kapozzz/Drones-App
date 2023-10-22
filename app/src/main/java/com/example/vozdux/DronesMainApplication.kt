package com.example.vozdux

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.vozdux.constants.DATASTORE_NAME
import com.example.vozdux.data.local.DronesDatabase
import com.example.vozdux.di.DaggerDroneComponent
import com.example.vozdux.di.DroneComponent
import javax.inject.Inject

class DronesMainApplication: Application() {

    lateinit var injector: DroneComponent

    @Inject
    lateinit var localDatabase: DronesDatabase

    override fun onCreate() {
        super.onCreate()
        injector = DaggerDroneComponent
            .builder()
            .context(applicationContext)
            .build()
        injector.inject(this)
    }
}