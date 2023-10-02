package com.example.vozdux.domain.usecase

import com.example.vozdux.domain.Repository
import com.example.vozdux.domain.model.Drone
import com.example.vozdux.domain.model.UploadDroneResult
import javax.inject.Inject

class InsertDroneUseCase @Inject constructor(
    private val repository: Repository
) {

    suspend operator fun invoke(drone: Drone): UploadDroneResult {
        return repository.insertDrone(drone)
    }
}