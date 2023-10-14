package com.example.vozdux.domain.usecase

import com.example.vozdux.domain.Repository
import com.example.vozdux.domain.model.drone.Image
import javax.inject.Inject

class InsertDroneImageUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(image: Image): Boolean {
        return repository.insertImage(image)
    }
}
