package com.example.vozdux.domain

import com.example.vozdux.domain.model.drone.Drone
import com.example.vozdux.domain.model.drone.DroneWithImages
import com.example.vozdux.domain.model.drone.Image
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getDrones(): Flow<List<DroneWithImages>>
    suspend fun getDroneById(droneId: String): DroneWithImages?
    suspend fun insertDrone(drone: Drone): Boolean
    suspend fun insertImage(image: Image): Boolean
}