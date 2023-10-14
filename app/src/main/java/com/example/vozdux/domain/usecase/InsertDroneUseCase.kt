package com.example.vozdux.domain.usecase

import com.example.vozdux.domain.Repository
import com.example.vozdux.domain.model.drone.Drone
import javax.inject.Inject

class InsertDroneUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(drone: Drone): Boolean {
        return repository.insertDrone(drone)
    }
}