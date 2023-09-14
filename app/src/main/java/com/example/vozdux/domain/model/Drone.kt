package com.example.vozdux.domain.model

data class Drone(
    val name: String,
    val shortDescription: String,
    val longDescription: List<Map<String, String>>,
    val properties: List<Map<String, String>>,
    val creationDate: String,
    val country: String,
    val cost: String,
    val id: String? = null
)





