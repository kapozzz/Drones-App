package com.example.vozdux.presenter.new_drone.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun NewDroneSecondaryHeader(
    onAddClick: () -> Unit,
    value: String,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = value,
            style = MaterialTheme.typography.titleSmall
        )

        Button(
            onClick = {
                onAddClick()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.background
            )
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = value,
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}