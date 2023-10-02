package com.example.vozdux.data

import android.util.Log
import com.example.vozdux.constants.emptyDrone
import com.example.vozdux.data.util.FirebaseHelper
import com.example.vozdux.data.util.FirebaseDefault
import com.example.vozdux.domain.Repository
import com.example.vozdux.domain.model.Drone
import com.example.vozdux.domain.model.UploadDroneImage
import com.example.vozdux.domain.model.UploadDroneImageResult
import com.example.vozdux.domain.model.UploadDroneResult
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val dronesHelper: FirebaseHelper
) : Repository {

    override suspend fun getDrones(): Flow<List<Drone>> {
        return flow {
            emit(listOf(emptyDrone))
        }
        // not working
    }

    override suspend fun getDroneById(droneId: String): Drone {
        return emptyDrone
        // not working
    }

    override suspend fun insertDrone(drone: Drone): UploadDroneResult {
        val database: DatabaseReference = dronesHelper.database
        var result: UploadDroneResult
        val tempDrone = drone.copy(
            id = if (drone.id.isNullOrEmpty()) dronesHelper.getNewKey() else drone.id
        )
        database.child(tempDrone.id!!).setValue(tempDrone)
            .addOnCompleteListener {
                result = UploadDroneResult(true, tempDrone.id)
            }
            .addOnCanceledListener {
                result = UploadDroneResult()
            }
            .addOnFailureListener {
                result = UploadDroneResult()
            }.addOnSuccessListener {
                result = UploadDroneResult(true, tempDrone.id)
            }
        return UploadDroneResult(true, tempDrone.id)
    }

    override suspend fun insertDroneImage(image: UploadDroneImage): UploadDroneImageResult {
        val path = dronesHelper.storage
            .child(
                image.id
            )
        path.putFile(image.uri)
        return UploadDroneImageResult()
    }
}