package com.example.vozdux.domain.model

import android.net.Uri
import java.util.UUID

data class UploadDroneImage(
    val uri: Uri,
    val id: String = UUID.randomUUID().toString()
)

data class UploadDroneImageResult(
    val isComplete: Boolean = false,
    val url: String? = null
)