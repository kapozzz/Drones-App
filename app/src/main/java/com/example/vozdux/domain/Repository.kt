package com.example.vozdux.domain

import com.example.vozdux.domain.model.Drone
import com.example.vozdux.domain.model.UploadDroneResult
import com.example.vozdux.domain.model.UploadDroneImage
import com.example.vozdux.domain.model.UploadDroneImageResult
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getDrones(): Flow<List<Drone>>
    suspend fun getDroneById(droneId: String): Drone
    suspend fun insertDrone(drone: Drone): UploadDroneResult
    suspend fun insertDroneImage(image: UploadDroneImage): UploadDroneImageResult
}