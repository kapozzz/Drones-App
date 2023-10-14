package com.example.vozdux.di

import android.content.Context
import com.example.vozdux.DronesMainApplication
import com.example.vozdux.di.data.DatabaseModule
import com.example.vozdux.presenter.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DatabaseModule::class])
interface  DroneComponent {
    fun inject(activity: MainActivity)

    fun inject(application: DronesMainApplication)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): DroneComponent
    }
}