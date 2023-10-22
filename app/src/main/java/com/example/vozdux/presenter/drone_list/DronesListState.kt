package com.example.vozdux.presenter.drone_list

import com.example.vozdux.domain.model.drone.DroneWithImages

data class DronesListState(
    val drones: List<DroneWithImages>
)

sealed class DroneListScreenState {

    object isLoading: DroneListScreenState()
    object isVisible: DroneListScreenState()

}
