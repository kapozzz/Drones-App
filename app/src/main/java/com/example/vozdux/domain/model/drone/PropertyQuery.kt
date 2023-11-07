package com.example.vozdux.domain.model.drone

data class PropertyQuery(
    val battery: Int = 0,
    val flightRange: Int = 0,
    val maxVelocity: Int = 0,
    val flightTime: Int = 0,
    val maximumFlightHeight: Int = 0
)
