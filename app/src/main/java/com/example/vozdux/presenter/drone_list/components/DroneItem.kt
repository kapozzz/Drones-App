package com.example.vozdux.presenter.drone_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vozdux.constants.test
import com.example.vozdux.domain.model.Drone

@Preview
@Composable
fun TestFunction() {
    DroneItem(
        drone = test,
        onClickAction = {

        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DroneItem(
    drone: Drone,
    onClickAction: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        onClick = {
            onClickAction.invoke(drone.id)
        }
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                modifier = Modifier
                    .padding(8.dp)
                    .size(120.dp),
                imageVector = Icons.Default.Clear,
                contentDescription = drone.name + "image"
            )

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(text = drone.name, style = MaterialTheme.typography.titleLarge)

                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp))

                Text(
                    text = drone.description,
                    maxLines = 3
                )
            }
        }
    }
}