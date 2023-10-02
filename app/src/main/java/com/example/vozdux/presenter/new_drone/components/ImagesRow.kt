package com.example.vozdux.presenter.new_drone.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.example.vozdux.domain.model.UploadDroneImage

@Composable
fun ImagesRow(
    value: List<UploadDroneImage>,
    modifier: Modifier = Modifier
) {

    val scrollState = rememberScrollState()

    Row(modifier = modifier.horizontalScroll(scrollState)) {

        value.forEach { droneImage ->

            AsyncImage(
                model = droneImage.uri,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
        }
    }
}