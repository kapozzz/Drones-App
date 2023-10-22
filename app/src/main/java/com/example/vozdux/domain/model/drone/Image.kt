package com.example.vozdux.domain.model.drone

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import java.util.UUID

data class Image(
    val uri: Uri? = null,
    val id: String = UUID.randomUUID().toString()
)

data class BmpImage(
    val bitmap: Bitmap,
    val id: String,
)

fun Bitmap.asImageBitmap(): ImageBitmap {
    return asImageBitmap()
}