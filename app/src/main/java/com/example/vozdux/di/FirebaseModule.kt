package com.example.vozdux.di

import com.example.vozdux.data.RepositoryImpl
import com.example.vozdux.data.util.FirebaseDefault
import com.example.vozdux.data.util.FirebaseHelper
import com.example.vozdux.domain.Repository
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [RepositoryBindModule::class])
 class FirebaseModule {

     @Provides
     fun provideDroneHelper(): FirebaseHelper {
         return FirebaseHelper(
             database = FirebaseDatabase.getInstance().getReference(
                 FirebaseDefault.DRONES
             ),
             storage = FirebaseStorage.getInstance().getReference(FirebaseDefault.DRONES_IMAGES_TABLE_NAME)
         )
     }

}

@Module
interface RepositoryBindModule {

    @Binds
    fun provideDronesRepository(impl: RepositoryImpl): Repository
}
