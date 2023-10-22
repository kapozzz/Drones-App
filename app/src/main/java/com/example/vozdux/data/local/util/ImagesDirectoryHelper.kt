package com.example.vozdux.data.local.util

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.example.vozdux.domain.model.drone.Image
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class ImagesDirectoryHelper @Inject constructor(
    private val context: Context
) {

    fun saveImageToInternalStorage(imageId: String, bitmap: Bitmap): Boolean {
        return try {
            with(context) {
                openFileOutput(imageId, MODE_PRIVATE).use { stream ->
                    if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 95, stream)) {
                        throw IOException("Couldn't save bitmap.")
                    }
                }
            }
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    suspend fun loadImagesFromInternalStorage(): List<Image> {
        return withContext(Dispatchers.IO) {
            with(context) {
                val files = filesDir.listFiles()
                files?.filter { it.canRead() && it.isFile }?.map {
                    val uri = Uri.fromFile(it)
                    Image(
                        id = it.name,
                        uri = uri
                    )
                } ?: emptyList()
            }
        }
    }
}