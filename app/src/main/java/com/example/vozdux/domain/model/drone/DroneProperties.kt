package com.example.vozdux.domain.model.drone

import java.util.UUID

data class DroneProperties(
    val battery: Int = 0,
    val flightRange: Int = 0,
    val maxVelocity: Int = 0,
    val flightTime: Int = 0,
    val maximumFlightHeight: Int = 0
) {

    fun toList(): List<PropertyElement> {

        val droneElements = mutableListOf<PropertyElement>()

        droneElements.add(PropertyElement("Battery", battery))
        droneElements.add(PropertyElement("Flight Range", flightRange))
        droneElements.add(PropertyElement("Max Velocity", maxVelocity))
        droneElements.add(PropertyElement("Flight Time", flightTime))
        droneElements.add(PropertyElement("Maximum Flight Height", maximumFlightHeight))

        return droneElements
    }
}

data class PropertyElement(
    val name: String,
    val value: Int,
    val id: String = UUID.randomUUID().toString()
)

fun List<PropertyElement>.toProperties(): DroneProperties {

    val properties = DroneProperties(
        battery = this.first {
            it.name == "Battery"
        }.value,
        flightRange = this.first {
            it.name == "Flight Range"
        }.value,
        maxVelocity = this.first {
            it.name == "Max Velocity"
        }.value,
        flightTime = this.first {
            it.name == "Flight Time"
        }.value,
        maximumFlightHeight = this.first {
            it.name == "Maximum Flight Height"
        }.value
    )
    return properties
}