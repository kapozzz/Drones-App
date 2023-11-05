package com.example.vozdux.domain.model.drone

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.vozdux.constants.DRONES_LOCAL_DATABASE_NAME
import com.example.vozdux.constants.EMPTY_ID
import com.example.vozdux.constants.EMPTY_STRING
import java.util.UUID

data class Drone(
    val name: String,
    val shortDescription: String,
    val longDescription: MutableList<CompositeDroneElement>,
    val otherProperties: MutableList<CompositeDroneElement>,
    val creationDate: String,
    val country: String,
    val cost: Cost,
    val images: List<UriImage>, // Id, source
    val battery: Int,
    val flightRange: Int,
    val maxVelocity: Int,
    val flightTime: Int,
    val maximumFlightHeight: Int,
    @PrimaryKey val id: String = EMPTY_ID
)

@Entity(tableName = DRONES_LOCAL_DATABASE_NAME)
data class UploadDrone(
    val name: String,
    val shortDescription: String,
    val longDescription: MutableList<CompositeDroneElement>,
    val otherProperties: MutableList<CompositeDroneElement>,
    val creationDate: String,
    val country: String,
    val cost: Cost,
    val images: List<Image>, // Id, source
    val battery: Int,
    val flightRange: Int,
    val maxVelocity: Int,
    val flightTime: Int,
    val maximumFlightHeight: Int,
    @PrimaryKey val id: String = EMPTY_ID
) {
    constructor() : this(
        name = EMPTY_STRING,
        shortDescription = EMPTY_STRING,
        longDescription = mutableListOf(),
        otherProperties = mutableListOf(),
        creationDate = EMPTY_STRING,
        country = EMPTY_STRING,
        images = mutableListOf(),
        battery = 0,
        flightRange = 0,
        maxVelocity = 0,
        flightTime = 0,
        maximumFlightHeight = 0,
        cost = Cost(
            value = EMPTY_STRING,
            currency = USD_CODE
        )
    )

    companion object {

        fun toDrone(uploadDrone: UploadDrone): Drone = Drone(
            name = uploadDrone.name,
            shortDescription = uploadDrone.shortDescription,
            longDescription = uploadDrone.longDescription,
            otherProperties = uploadDrone.otherProperties,
            creationDate = uploadDrone.creationDate,
            country = uploadDrone.country,
            cost = uploadDrone.cost,
            images = uploadDrone.images.map {
                UriImage(
                    id = it.id,
                    source = it.source,
                    uri = Uri.parse(it.uri)
                )
            },
            battery = uploadDrone.battery,
            flightRange = uploadDrone.flightRange,
            maxVelocity = uploadDrone.maxVelocity,
            flightTime = uploadDrone.flightTime,
            maximumFlightHeight = uploadDrone.maximumFlightHeight,
            id = uploadDrone.id
        )

        fun fromDrone(drone: Drone): UploadDrone = UploadDrone(
            name = drone.name,
            shortDescription = drone.shortDescription,
            longDescription = drone.longDescription,
            otherProperties = drone.otherProperties,
            creationDate = drone.creationDate,
            country = drone.country,
            cost = drone.cost,
            images = drone.images.map {
                Image(
                    id = it.id,
                    source = it.source,
                    uri = it.uri.toString()
                )
            },
            battery = drone.battery,
            flightRange = drone.flightRange,
            maxVelocity = drone.maxVelocity,
            flightTime = drone.flightTime,
            maximumFlightHeight = drone.maximumFlightHeight,
            id = drone.id
        )
    }
}

data class Image(
    val id: String = UUID.randomUUID().toString(),
    val source: String = EMPTY_STRING,
    val uri: String = EMPTY_STRING
)

data class UriImage(
    val uri: Uri? = null,
    val id: String = UUID.randomUUID().toString(),
    val source: String = EMPTY_STRING
)

data class CompositeDroneElement(
    val name: String = EMPTY_STRING,
    val value: String = EMPTY_STRING,
    val id: String = UUID.randomUUID().toString()
)







