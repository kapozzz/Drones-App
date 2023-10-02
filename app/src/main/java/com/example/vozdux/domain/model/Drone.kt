package com.example.vozdux.domain.model

import java.util.UUID

data class Drone(
    val name: String,
    val shortDescription: String,
    val longDescription: MutableList<CompositeDroneElement>,
    val properties: MutableList<CompositeDroneElement>,
    val creationDate: String,
    val country: String,
    val cost: Cost,
    val images: List<DroneImage>,
    val id: String? = null,
)

data class CompositeDroneElement(
    val name: String = "",
    val value: String = "",
    val id: String = UUID.randomUUID().toString()
)







