package com.example.vozdux.domain.usecase

import com.example.vozdux.domain.Repository
import com.example.vozdux.domain.model.Drone
import kotlinx.coroutines.flow.toList
import java.util.concurrent.Flow
import javax.inject.Inject


class GetDronesUseCase @Inject constructor(
    private val repository: Repository
) {

    suspend operator fun invoke() {
        TODO()
    }
}