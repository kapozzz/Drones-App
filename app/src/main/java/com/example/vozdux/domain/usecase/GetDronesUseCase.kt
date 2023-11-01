package com.example.vozdux.domain.usecase

import com.example.vozdux.domain.Repository
import com.example.vozdux.domain.model.drone.Drone
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDronesUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(): Flow<List<Drone>> {
        return repository.getDrones()
    }
}