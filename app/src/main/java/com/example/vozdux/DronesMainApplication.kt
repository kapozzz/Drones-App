package com.example.vozdux

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.util.DebugLogger
import com.example.vozdux.constants.DATASTORE_NAME
import com.example.vozdux.data.local.DronesDatabase
import com.example.vozdux.di.DaggerDroneComponent
import com.example.vozdux.di.DroneComponent
import javax.inject.Inject

class DronesMainApplication: Application(), ImageLoaderFactory {

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

    override fun newImageLoader(): ImageLoader {
        return ImageLoader(this).newBuilder()
            .memoryCachePolicy(CachePolicy.ENABLED)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizeBytes(0.1.toInt())
                    .strongReferencesEnabled(true)
                    .build()
            }
            .diskCachePolicy(CachePolicy.ENABLED)
            .diskCache {
                DiskCache.Builder()
                    .maxSizePercent(0.03)
                    .directory(cacheDir)
                    .build()
            }
            .logger(DebugLogger())
            .build()
    }
}