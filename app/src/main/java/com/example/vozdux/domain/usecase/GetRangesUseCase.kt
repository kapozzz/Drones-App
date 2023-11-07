package com.example.vozdux.domain.usecase

import com.example.vozdux.domain.Repository
import com.example.vozdux.domain.model.other.PropertiesRanges
import javax.inject.Inject

class GetRangesUseCase @Inject constructor(
    private val repository: Repository
) {

    suspend operator fun invoke(): PropertiesRanges {
        return repository.getRanges()
    }
}