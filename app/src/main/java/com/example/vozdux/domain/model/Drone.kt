package com.example.vozdux.domain.model

data class Drone(
    val name: String,
    val shortDescription: String,
    val longDescription: MutableList<CompositeDroneElement>,
    val properties: MutableList<CompositeDroneElement>,
    val creationDate: String,
    val country: String,
    val cost: String,
    val id: String? = null
)

data class CompositeDroneElement(
    val name: String = "",
    val value: String = ""
)







