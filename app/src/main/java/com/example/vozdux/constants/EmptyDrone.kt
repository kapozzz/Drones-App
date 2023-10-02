package com.example.vozdux.constants

import com.example.vozdux.domain.model.Cost
import com.example.vozdux.domain.model.Currency
import com.example.vozdux.domain.model.Drone
import com.example.vozdux.domain.model.DroneImage

val emptyDrone = Drone(
    name = "",
    shortDescription = "",
    longDescription = mutableListOf(),
    properties = mutableListOf(),
    creationDate = "",
    country = "",
    images = mutableListOf(),
    cost = Cost(
        value = "",
        currency = Currency.USD
    )
)

