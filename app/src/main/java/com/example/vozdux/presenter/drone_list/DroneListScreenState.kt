package com.example.vozdux.presenter.drone_list

sealed class DroneListScreenState {

    object Loading: DroneListScreenState()
    object Screen: DroneListScreenState()
    object Search: DroneListScreenState()
}
