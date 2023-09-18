package com.example.vozdux.di

import com.example.vozdux.presenter.MainActivity
import dagger.Component

@Component(modules = [FirebaseModule::class])
interface  DroneComponent {
    fun inject(activity: MainActivity)
}