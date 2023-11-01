package com.example.vozdux.presenter.drone_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ImageNotSupported
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.vozdux.R
import com.example.vozdux.domain.model.drone.Drone

@Composable
fun DroneItem(
    element: Drone,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(4.dp)
            )
            .background(MaterialTheme.colorScheme.secondary),
        shape = RoundedCornerShape(4.dp),
        onClick = {
            onClick.invoke(element.id)
        }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.secondary),
                contentAlignment = Alignment.Center
            ) {
                if (element.images.isNotEmpty()) {
                    AsyncImage(
                        model = element.images[0].uri,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(256.dp),
                        contentScale = ContentScale.Crop)
                }
                else Image(
                    modifier = Modifier
                        .padding(60.dp)
                        .size(60.dp),
                    imageVector = Icons.Default.ImageNotSupported,
                    contentDescription = element.name + stringResource(R.string.image)
                )
            }
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = element.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                )
                Text(
                    text = element.shortDescription,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}