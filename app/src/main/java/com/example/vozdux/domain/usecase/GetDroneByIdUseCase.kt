package com.example.vozdux.domain.usecase

import com.example.vozdux.domain.Repository
import com.example.vozdux.domain.model.drone.Drone
import com.example.vozdux.domain.model.drone.DroneWithImages
import javax.inject.Inject

class GetDroneByIdUseCase @Inject constructor(
    private val repository: Repository
) {

    suspend operator fun invoke(droneId: String): DroneWithImages? {
        return repository.getDroneById(droneId)
    }
}