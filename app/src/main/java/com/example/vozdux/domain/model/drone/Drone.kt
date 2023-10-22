package com.example.vozdux.domain.model.drone

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.vozdux.constants.DRONES_LOCAL_DATABASE_NAME
import com.example.vozdux.constants.EMPTY_ID
import com.example.vozdux.constants.EMPTY_STRING
import java.util.UUID


@Entity(tableName = DRONES_LOCAL_DATABASE_NAME)
data class Drone (
    val name: String,
    val shortDescription: String,
    val longDescription: MutableList<CompositeDroneElement>,
    val otherProperties: MutableList<CompositeDroneElement>,
    val mainProperties: DroneProperties,
    val creationDate: String,
    val country: String,
    val cost: Cost,
    val imageIDs: List<ImageSourceId>, // Id, source
    @PrimaryKey val id: String = EMPTY_ID,
) {

    constructor() : this(
        name = EMPTY_STRING,
        shortDescription = EMPTY_STRING ,
        longDescription = mutableListOf(),
        otherProperties = mutableListOf(),
        creationDate = EMPTY_STRING,
        country = EMPTY_STRING,
        imageIDs = mutableListOf(),
        cost = Cost(
            value = EMPTY_STRING,
            currency = USD_CODE
        ),
        mainProperties = DroneProperties(
            battery = 0,
            flightRange = 0,
            flightTime = 0,
            maximumFlightHeight = 0,
            maxVelocity = 0
        )
    )
}

data class ImageSourceId(
    val id: String = EMPTY_STRING,
    val source: String = EMPTY_STRING
)
data class CompositeDroneElement(
    val name: String = EMPTY_STRING,
    val value: String = EMPTY_STRING,
    val id: String = UUID.randomUUID().toString()
)







