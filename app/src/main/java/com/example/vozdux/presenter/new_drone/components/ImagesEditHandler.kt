package com.example.vozdux.presenter.new_drone.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.vozdux.R
import com.example.vozdux.domain.model.drone.UriImage
import com.example.vozdux.presenter.new_drone.NewDroneScreenEvent

@Composable
fun ImagesEditHandler(
    images: List<UriImage>,
    onEvent: (event: NewDroneScreenEvent) -> Unit,
    modifier: Modifier = Modifier,
) {

    val imageToDelete = remember {
        mutableStateOf(if (images.isNotEmpty()) images.first() else null)
    }

    val showImage = remember {
        mutableStateOf(false)
    }

    val alertDialogState = remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .shadow(2.dp, shape = RoundedCornerShape(4.dp))
            .background(
                color = MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(4.dp)
            ),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState())
        ) {
            repeat(images.size) { page ->
                val currentImage = images[page]
                Box(
                    modifier = Modifier.size(140.dp),
                    contentAlignment = Alignment.TopEnd
                ) {

                    AsyncImage(
                        model = currentImage.uri,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable { onEvent(NewDroneScreenEvent.ShowImage(currentImage)) },
                        contentScale = ContentScale.FillBounds,
                        alignment = Alignment.Center
                    )

                    IconButton(
                        modifier = Modifier
                            .padding(8.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray.copy(alpha = 0.60F)),
                        onClick = {
                            imageToDelete.value = currentImage
                            alertDialogState.value = true
                        }
                    ) {
                        Icon(
                            modifier = Modifier,
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(R.string.delete_image),
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }


    if (alertDialogState.value) AlertDialog(
        title =
        {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Delete the image?",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Left
            )
        },
        buttons = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = { alertDialogState.value = false }) {
                    Text(
                        text = stringResource(R.string.dismiss),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
                TextButton(onClick = {
                    imageToDelete.value?.let {
                        onEvent(NewDroneScreenEvent.DeleteUriImage(imageToDelete.value!!))
                    }
                    alertDialogState.value = false
                }) {
                    Text(
                        text = stringResource(R.string.confirm),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        },
        onDismissRequest = { alertDialogState.value = false }
    )
}