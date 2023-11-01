package com.example.vozdux.presenter.generalComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.example.vozdux.domain.model.drone.UriImage

@Composable
fun ImageScreen(
    image: UriImage,
    onCloseClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray.copy(alpha = 0.70F))
            .clickable { onCloseClick() },
        contentAlignment = Alignment.Center
    ) {

        AsyncImage(
            model = image.uri,
            contentDescription = null,
            modifier = Modifier,
            contentScale = ContentScale.FillWidth,
            alignment = Alignment.Center
        )
    }
}