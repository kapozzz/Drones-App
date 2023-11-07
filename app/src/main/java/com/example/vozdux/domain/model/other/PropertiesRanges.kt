package com.example.vozdux.domain.model.other

data class PropertiesRanges(
    val battery: ClosedFloatingPointRange<Float>,
    val flightRange: ClosedFloatingPointRange<Float>,
    val maxVelocity: ClosedFloatingPointRange<Float>,
    val flightTime: ClosedFloatingPointRange<Float>,
    val maximumFlightHeight: ClosedFloatingPointRange<Float>
)
