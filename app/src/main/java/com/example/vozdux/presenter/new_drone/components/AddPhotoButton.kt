package com.example.vozdux.presenter.new_drone.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.vozdux.R
import com.example.vozdux.domain.model.drone.UriImage
import com.example.vozdux.presenter.new_drone.NewDroneScreenEvent

@Composable
fun AddPhotoButton(
    onEvent: (event: NewDroneScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uris ->
            val changedUris = uris.map { newUri ->
                UriImage(
                    uri = newUri
                )
            }
            onEvent(
                NewDroneScreenEvent.UrisChanged(
                    changedUris
                )
            )
        }
    )

    Button(
        shape = RoundedCornerShape(4.dp),
        modifier = modifier,
        onClick = {
            photoPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }
    ) {
        Icon(
            imageVector = Icons.Default.AddAPhoto,
            contentDescription = stringResource(R.string.add_photo)
        )
    }
}