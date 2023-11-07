package com.example.vozdux.presenter.drone_list

import com.example.vozdux.domain.model.other.DroneOrder

sealed class DroneListEvent {

    data class QueryChanged(val query: String): DroneListEvent()

    data class NewOrder(val order: DroneOrder): DroneListEvent()

    object FilterOnClick: DroneListEvent()

    object CloseSearchScreen: DroneListEvent()

    object OnSearch: DroneListEvent()
}
