package com.example.vozdux.domain.model.drone

import android.net.Uri
import com.example.vozdux.constants.EMPTY_STRING
import java.util.UUID

data class Image(
    val uri: Uri? = null,
//    val source: String = EMPTY_STRING,
    val id: String = UUID.randomUUID().toString()
)
