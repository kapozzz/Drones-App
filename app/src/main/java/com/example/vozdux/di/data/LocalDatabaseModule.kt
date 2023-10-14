package com.example.vozdux.di.data

import android.content.Context
import androidx.room.Room
import com.example.vozdux.data.local.DronesDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalDatabaseModule {

    @Singleton
    @Provides
    fun provideLocalDatabase(context: Context): DronesDatabase = Room.databaseBuilder(
        context,
        DronesDatabase::class.java,
        "DronesLocalDatabase"
    )
        .fallbackToDestructiveMigration()
        .build()
}