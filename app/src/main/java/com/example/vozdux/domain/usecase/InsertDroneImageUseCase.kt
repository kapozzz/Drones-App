package com.example.vozdux.domain.usecase

import com.example.vozdux.domain.Repository
import com.example.vozdux.domain.model.UploadDroneImage
import com.example.vozdux.domain.model.UploadDroneImageResult
import javax.inject.Inject

class InsertDroneImageUseCase @Inject constructor(
    private val repository: Repository
) {

    suspend operator fun invoke(image: UploadDroneImage): UploadDroneImageResult {
        return repository.insertDroneImage(image)
    }
}
