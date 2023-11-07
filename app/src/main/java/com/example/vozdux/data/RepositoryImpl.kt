package com.example.vozdux.data

import com.example.vozdux.data.local.DronesDatabase
import com.example.vozdux.data.remote.FirebaseDatabase
import com.example.vozdux.domain.Repository
import com.example.vozdux.domain.model.drone.Drone
import com.example.vozdux.domain.model.drone.UploadDrone
import com.example.vozdux.domain.model.other.PropertiesRanges
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    private val remoteDatabase: FirebaseDatabase,
    private val localDatabase: DronesDatabase,
) : Repository {

    init {
        CoroutineScope(Dispatchers.IO).launch {
            remoteDatabase.dronesStateFlow.collect { drones ->
                val currentLocalDronesList = localDatabase.dao().getAllDrones().first()
                val currentRemoteDronesIDs = drones.map { it.id }
                currentLocalDronesList.forEach { uploadDrone ->
                    if (uploadDrone.id !in currentRemoteDronesIDs) localDatabase.dao()
                        .deleteDrone(uploadDrone)
                }
                localDatabase.dao().insertListOfDrones(drones)
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

    override suspend fun getRanges(): PropertiesRanges {
        val drones = localDatabase.dao().getAllDrones().first()
        return PropertiesRanges(
            battery = drones.minBy { it.battery }.battery.toFloat()..drones.maxBy { it.battery.toFloat() }.battery.toFloat(),
            flightRange = drones.minBy { it.flightRange }.flightRange.toFloat()..drones.maxBy { it.flightRange.toFloat() }.flightRange.toFloat(),
            maxVelocity = drones.minBy { it.maxVelocity }.maxVelocity.toFloat()..drones.maxBy { it.maxVelocity.toFloat() }.maxVelocity.toFloat(),
            flightTime = drones.minBy { it.flightTime }.flightTime.toFloat()..drones.maxBy { it.flightTime.toFloat() }.flightTime.toFloat(),
            maximumFlightHeight = drones.minBy { it.maximumFlightHeight }.maximumFlightHeight.toFloat()..drones.maxBy { it.maximumFlightHeight.toFloat() }.maximumFlightHeight.toFloat(),
        )
    }
}