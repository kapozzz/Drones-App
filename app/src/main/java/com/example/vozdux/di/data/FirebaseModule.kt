package com.example.vozdux.di.data

import com.example.vozdux.constants.DRONES_IMAGES_STORAGE_CHILD_NAME
import com.example.vozdux.constants.DRONES_REMOTE_DATABASE_CHILD_NAME
import com.example.vozdux.data.util.FirebaseHelper
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides

@Module(includes = [RepositoryBindModule::class])
 class FirebaseModule {
     @Provides
     fun provideDroneHelper(): FirebaseHelper {
         return FirebaseHelper(
             database = FirebaseDatabase.getInstance().getReference(
                 DRONES_REMOTE_DATABASE_CHILD_NAME
             ),
             storage = FirebaseStorage.getInstance().getReference(DRONES_IMAGES_STORAGE_CHILD_NAME)
         )
     }
}

