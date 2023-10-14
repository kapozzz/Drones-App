package com.example.vozdux.data.local.util

import android.content.Context
import android.net.Uri
import android.os.Environment
import com.example.vozdux.domain.model.drone.Image
import java.io.File
import javax.inject.Inject

class Pather @Inject constructor(private val context: Context) {

    fun path(image: Image): ImageEntity {
        return ImageEntity(
            id = image.id,
            path = File(
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                image.id
            ).absolutePath
        )
    }

    fun unPath(imageEntity: ImageEntity): Image {
        return Image(
            uri = Uri.fromFile(File(imageEntity.path)),
            id = imageEntity.id
        )
    }
}