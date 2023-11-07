package com.example.vozdux.domain

import com.example.vozdux.domain.model.drone.Drone
import com.example.vozdux.domain.model.other.PropertiesRanges
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getDrones(): Flow<List<Drone>>
    suspend fun getDroneById(droneId: String): Drone?
    suspend fun insertDrone(drone: Drone): Boolean
    suspend fun getRanges(): PropertiesRanges
}