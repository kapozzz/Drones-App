package com.example.vozdux.data

import com.example.vozdux.constants.emptyDrone
import com.example.vozdux.data.util.FirebaseStorages
import com.example.vozdux.domain.model.Drone
import com.example.vozdux.domain.Repository
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(): Repository {

    val dbName = FirebaseStorages.DRONES
    private val database: DatabaseReference =  FirebaseDatabase.getInstance().getReference(dbName)

    override suspend fun getDrones(): Flow<List<Drone>> {
        return flow {
            emit(listOf(emptyDrone))
        }
    }

    override suspend fun getDroneById(droneId: String): Drone {
        return emptyDrone
    }

    override suspend fun insertDrone(drone: Drone): Boolean {
        var result = false
        var _drone = drone
        if (_drone.id == null) {
            val newId = database.push().key!!
            _drone = drone.copy(
                id = newId
            )
        }
        database.child(_drone.id!!).setValue(drone)
            .addOnCompleteListener {
                result = true
            }
        return result
    }
}