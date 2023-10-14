package com.example.vozdux.domain.usecase

import javax.inject.Inject

data class UseCases @Inject constructor(
    val getDrones: GetDronesUseCase,
    val getDroneById: GetDroneByIdUseCase,
    val insertDrone: InsertDroneUseCase,
    val insertImage: InsertDroneImageUseCase
)