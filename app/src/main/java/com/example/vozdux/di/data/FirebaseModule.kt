package com.example.vozdux.di.data

import com.example.vozdux.constants.DRONES_IMAGES_STORAGE_CHILD_NAME
import com.example.vozdux.constants.DRONES_REMOTE_DATABASE_CHILD_NAME
import com.example.vozdux.data.util.COUNTER_HELPER_NAME
import com.example.vozdux.data.util.DATABASE_COUNTER_NAME
import com.example.vozdux.data.util.DRONE_HELPER_NAME
import com.example.vozdux.data.util.FirebaseHelper
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module(includes = [RepositoryBindModule::class])
class FirebaseModule {

    @Named(DRONE_HELPER_NAME)
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

