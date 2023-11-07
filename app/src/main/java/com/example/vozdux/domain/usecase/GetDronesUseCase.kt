package com.example.vozdux.domain.usecase

import com.example.vozdux.domain.Repository
import com.example.vozdux.domain.model.drone.Drone
import com.example.vozdux.domain.model.other.DroneOrder
import com.example.vozdux.domain.model.other.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetDronesUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(
        query: String? = null,
        droneOrder: DroneOrder? = null
    ): Flow<List<Drone>> {
        val dronesFlow = repository.getDrones()
        return if (query == null) dronesFlow
        else dronesFlow.map { drones ->
            when (droneOrder!!.orderType) {
                is OrderType.Ascending -> {
                    when (droneOrder) {
                        is DroneOrder.Battery -> drones.sortedBy { it.battery }
                        is DroneOrder.FlightRange -> drones.sortedBy { it.flightRange }
                        is DroneOrder.FlightTime -> drones.sortedBy { it.flightTime }
                        is DroneOrder.MaxVelocity -> drones.sortedBy { it.maxVelocity }
                        is DroneOrder.MaximumFlightHeight -> drones.sortedBy { it.maximumFlightHeight }
                    }
                }

                is OrderType.Descending -> {
                    when (droneOrder) {
                        is DroneOrder.Battery -> drones.sortedByDescending { it.battery }
                        is DroneOrder.FlightRange -> drones.sortedByDescending { it.flightRange }
                        is DroneOrder.FlightTime -> drones.sortedByDescending { it.flightTime }
                        is DroneOrder.MaxVelocity -> drones.sortedByDescending { it.maxVelocity }
                        is DroneOrder.MaximumFlightHeight -> drones.sortedByDescending { it.maximumFlightHeight }
                    }
                }
            }.filter { drone ->
                query.lowercase() in drone.name.lowercase()
            }
        }
    }
}