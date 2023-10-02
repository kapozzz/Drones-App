package com.example.vozdux.domain.usecase

import javax.inject.Inject

data class UseCases @Inject constructor(
    val getDronesUseCase: GetDronesUseCase,
    val insertDroneUseCase: InsertDroneUseCase,
    val insertDroneImageUseCase: InsertDroneImageUseCase
)