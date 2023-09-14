package com.example.vozdux.data

import com.example.vozdux.constants.test
import com.example.vozdux.data.util.FirebaseStorages
import com.example.vozdux.domain.model.Drone
import com.example.vozdux.domain.Repository
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Named
import kotlin.jvm.Throws

class RepositoryImpl @Inject constructor(
    @param:Named(FirebaseStorages.DRONES)
    private val database: DatabaseReference
): Repository {

    override suspend fun getDrones(): Flow<List<Drone>> {
        return flow {
            emit(listOf(test))
        }
    }

    override suspend fun getDroneById(droneId: String): Drone {
        return test
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