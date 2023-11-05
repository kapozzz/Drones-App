package com.example.vozdux.constants

import com.example.vozdux.domain.model.drone.Cost
import com.example.vozdux.domain.model.drone.Drone
import com.example.vozdux.domain.model.drone.DroneProperties
import com.example.vozdux.domain.model.drone.USD_CODE

val emptyDrone = Drone(
    name = EMPTY_STRING,
    shortDescription = EMPTY_STRING ,
    longDescription = mutableListOf(),
    otherProperties = mutableListOf(),
    creationDate = EMPTY_STRING,
    country = EMPTY_STRING,
    images = mutableListOf(),
    cost = Cost(
        value = EMPTY_STRING,
        currency = USD_CODE
    ),
    battery = 0,
    maxVelocity = 0,
    flightRange = 0,
    flightTime = 0,
    maximumFlightHeight = 0
)

