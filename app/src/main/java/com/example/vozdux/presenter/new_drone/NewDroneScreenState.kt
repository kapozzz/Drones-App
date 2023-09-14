package com.example.vozdux.presenter.new_drone

import com.example.vozdux.domain.model.Drone

data class NewDroneScreenState(
    val currentDrone: Drone = Drone(
        name = "",
        shortDescription = "",
        longDescription = emptyList(),
        properties = emptyList(),
        creationDate = "",
        country = "",
        cost = ""
    )
)

