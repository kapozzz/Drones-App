package com.example.vozdux.domain.model.drone

data class DroneWithImages(
    val drone: Drone,
    val images: List<Image> = emptyList()
)