package com.example.vozdux.data

import android.util.Log
import com.example.vozdux.data.local.DronesDatabase
import com.example.vozdux.data.remote.FirebaseDatabase
import com.example.vozdux.domain.Repository
import com.example.vozdux.domain.model.drone.Drone
import com.example.vozdux.domain.model.drone.UploadDrone
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    private val remoteDatabase: FirebaseDatabase,
    private val localDatabase: DronesDatabase,
) : Repository {

    private val scope = CoroutineScope(Dispatchers.Default)
    private var job: Job? = null

    init {
        CoroutineScope(Dispatchers.IO).launch {
            remoteDatabase.dronesStateFlow.collect { drones ->
                job?.cancel()
                job = scope.launch {
                    val currentLocalDronesList = localDatabase.dao().getAllDrones().first()
                    val currentRemoteDronesIDs = drones.map { it.id }
                    currentLocalDronesList.forEach { uploadDrone ->
                        if (uploadDrone.id !in currentRemoteDronesIDs) localDatabase.dao().deleteDrone(uploadDrone)
                    }
                    localDatabase.dao().insertListOfDrones(drones)
                }
            }
        }
    }

    override suspend fun getDrones(): Flow<List<Drone>> {
        return localDatabase.dao().getAllDrones()
            .map { drones ->
                if (drones.isNotEmpty()) {
                    drones.map { uploadDrone -> UploadDrone.toDrone(uploadDrone) }
                } else emptyList()
            }.flowOn(Dispatchers.IO)
    }

    override suspend fun getDroneById(droneId: String): Drone {
        return withContext(Dispatchers.IO) {
            return@withContext UploadDrone.toDrone(localDatabase.dao().getDroneById(droneId))
        }
    }

    override suspend fun insertDrone(drone: Drone): Boolean {
        return remoteDatabase.insertDrone(drone)
    }
}