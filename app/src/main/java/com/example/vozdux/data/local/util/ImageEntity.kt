package com.example.vozdux.data.local.util

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.vozdux.constants.IMAGES_LOCAL_DATABASE_NAME

@Entity(tableName = IMAGES_LOCAL_DATABASE_NAME)
data class ImageEntity(
    @PrimaryKey val id: String,
    val path: String
)
