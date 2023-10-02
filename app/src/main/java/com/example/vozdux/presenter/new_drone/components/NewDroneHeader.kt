package com.example.vozdux.presenter.new_drone.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NewDroneHeader(
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopStart
    ) {
        Text(
            text = "Drone constructor",
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
            style = MaterialTheme.typography.titleMedium
        )
    }
}