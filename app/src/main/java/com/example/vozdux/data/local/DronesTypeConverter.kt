package com.example.vozdux.data.local

import android.net.Uri
import android.util.JsonReader
import android.util.JsonWriter
import androidx.room.TypeConverter
import com.example.vozdux.domain.model.drone.CompositeDroneElement
import com.example.vozdux.domain.model.drone.Cost
import com.example.vozdux.domain.model.drone.DroneProperties
import com.example.vozdux.domain.model.drone.Image
import com.example.vozdux.domain.model.drone.UriImage
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken

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
    fun fromCostToJson(cost: Cost): String = Gson().toJson(cost)

    @TypeConverter
    fun fromJsonToCost(jsonCost: String): Cost = Gson().fromJson(jsonCost, Cost::class.java)

    @TypeConverter
    fun fromImagesToJson(images: List<Image>): String = Gson().toJson(images)

    @TypeConverter
    fun fromJsonToImages(jsonImages: String): List<Image> {
        val type = object : TypeToken<List<Image>>() {}.type
        return Gson().fromJson(jsonImages, type)
    }
}



