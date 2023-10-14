package com.example.vozdux.di.data

import com.example.vozdux.data.RepositoryImpl
import com.example.vozdux.domain.Repository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(includes = [LocalDatabaseModule::class, FirebaseModule::class])
class DatabaseModule

@Module
interface RepositoryBindModule {
    @Binds
    fun provideDronesRepository(impl: RepositoryImpl): Repository
}
