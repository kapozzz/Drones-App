package com.example.vozdux.data.local

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.room.TypeConverter
import com.example.vozdux.data.local.util.ImageEntity
import com.example.vozdux.domain.model.drone.CompositeDroneElement
import com.example.vozdux.domain.model.drone.Cost
import com.example.vozdux.domain.model.drone.Drone
import com.example.vozdux.domain.model.drone.DroneProperties
import com.example.vozdux.domain.model.drone.Image
import com.example.vozdux.domain.model.drone.ImageSourceId
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import javax.inject.Inject

class DronesTypeConverter {

    @TypeConverter
    fun fromCompositeElementToJson(compositeDroneElement: List<CompositeDroneElement>): String =
        Gson().toJson(compositeDroneElement)

    @TypeConverter
    fun fromJsonToCompositeElement(jsonCompositeDroneElement: String): List<CompositeDroneElement> {
        val type = object : TypeToken<List<CompositeDroneElement>>() {}.type
        return Gson().fromJson(jsonCompositeDroneElement, type)
    }

    @TypeConverter
    fun fromMainPropertiesToJson(mainProperties: DroneProperties): String =
        Gson().toJson(mainProperties)

    @TypeConverter
    fun fromJsonToMainProperties(jsonMainProperties: String): DroneProperties =
        Gson().fromJson(jsonMainProperties, DroneProperties::class.java)

    @TypeConverter
    fun fromCostToJson(cost: Cost): String = Gson().toJson(cost)

    @TypeConverter
    fun fromJsonToCost(jsonCost: String): Cost = Gson().fromJson(jsonCost, Cost::class.java)

    @TypeConverter
    fun fromImageIDsToJson(list: List<ImageSourceId>): String = Gson().toJson(list)

    @TypeConverter
    fun fromJsonToIDs(jsonList: String): List<ImageSourceId> {
        val type = object : TypeToken<List<ImageSourceId>>() {}.type
        return Gson().fromJson(jsonList, type)
    }
}
