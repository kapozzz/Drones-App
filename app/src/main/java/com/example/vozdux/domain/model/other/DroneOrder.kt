package com.example.vozdux.domain.model.other

sealed class DroneOrder(val orderType: OrderType) {
    class Battery(orderType: OrderType): DroneOrder(orderType)
    class FlightRange(orderType: OrderType): DroneOrder(orderType)
    class MaxVelocity(orderType: OrderType): DroneOrder(orderType)
    class FlightTime(orderType: OrderType): DroneOrder(orderType)
    class MaximumFlightHeight(orderType: OrderType): DroneOrder(orderType)

    fun copy(orderType: OrderType): DroneOrder {
        return when(this) {
            is Battery -> Battery(orderType)
            is FlightRange -> FlightRange(orderType)
            is MaxVelocity -> MaxVelocity(orderType)
            is FlightTime -> FlightTime(orderType)
            is MaximumFlightHeight -> MaximumFlightHeight(orderType)
        }
    }
}
