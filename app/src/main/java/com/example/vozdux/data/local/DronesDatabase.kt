package com.example.vozdux.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.vozdux.data.util.LOCAL_DATABASE_VERSION
import com.example.vozdux.domain.model.drone.Drone

@TypeConverters(DronesTypeConverter::class)
@Database(
    version = LOCAL_DATABASE_VERSION,
    entities = [Drone::class],
    exportSchema = false
)
abstract class DronesDatabase: RoomDatabase() {
    abstract fun dao(): DronesDao
}