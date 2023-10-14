package com.example.vozdux.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vozdux.constants.DRONES_LOCAL_DATABASE_NAME
import com.example.vozdux.constants.IMAGES_LOCAL_DATABASE_NAME
import com.example.vozdux.data.local.util.ImageEntity
import com.example.vozdux.domain.model.drone.Drone
import kotlinx.coroutines.flow.Flow

@Dao
interface DronesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListOfDrones(drones: List<Drone>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDrone(drone: Drone)

    @Query("SELECT * FROM $DRONES_LOCAL_DATABASE_NAME")
    fun getAllDrones(): Flow<List<Drone>>

    @Query("SELECT * FROM $DRONES_LOCAL_DATABASE_NAME WHERE id=:droneId ")
    fun getDroneById(droneId: String): Drone

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertListOfImages(images: List<ImageEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertImage(imageEntity: ImageEntity)

    @Query("SELECT * FROM $IMAGES_LOCAL_DATABASE_NAME WHERE id=:imageId ")
    fun getImageById(imageId: String): ImageEntity
}