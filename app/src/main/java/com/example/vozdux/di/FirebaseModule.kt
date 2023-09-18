package com.example.vozdux.di

import com.example.vozdux.data.RepositoryImpl
import com.example.vozdux.data.util.FirebaseStorages
import com.example.vozdux.domain.Repository
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.Binds
import dagger.BindsInstance
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module(includes = [RepositoryBindModule::class])
 class FirebaseModule {

}

@Module
interface RepositoryBindModule {

    @Binds
    fun provideDronesRepository(impl: RepositoryImpl): Repository
}
