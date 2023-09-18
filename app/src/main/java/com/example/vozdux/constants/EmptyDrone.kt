package com.example.vozdux.constants

import com.example.vozdux.domain.model.Drone

val emptyDrone = Drone(
    name = "",
    shortDescription = "",
    longDescription = mutableListOf(),
    properties = mutableListOf(),
    creationDate = "",
    country = "",
    cost = ""
)

