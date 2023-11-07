package com.example.vozdux.presenter.drone_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.vozdux.domain.model.other.DroneOrder
import com.example.vozdux.domain.model.other.OrderType

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    droneOrder: DroneOrder = DroneOrder.MaxVelocity(OrderType.Descending),
    onOrderChange: (DroneOrder) -> Unit
) {

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.background(MaterialTheme.colorScheme.secondary),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {

        Column(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary)) {

            Row(modifier = Modifier.fillMaxWidth()) {

                DefaultRadioButton(
                    text = "Battery",
                    selected = droneOrder is DroneOrder.Battery,
                    onSelect = { onOrderChange(DroneOrder.Battery(droneOrder.orderType)) })

                Spacer(modifier = Modifier.width(8.dp))

                DefaultRadioButton(
                    text = "MaxVelocity",
                    selected = droneOrder is DroneOrder.MaxVelocity,
                    onSelect = { onOrderChange(DroneOrder.MaxVelocity(droneOrder.orderType)) })

            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(modifier = Modifier.fillMaxWidth()) {

                DefaultRadioButton(
                    text = "Flight Range",
                    selected = droneOrder is DroneOrder.FlightRange,
                    onSelect = { onOrderChange(DroneOrder.FlightRange(droneOrder.orderType)) })


                Spacer(modifier = Modifier.width(8.dp))

                DefaultRadioButton(
                    text = "Flight Time Range",
                    selected = droneOrder is DroneOrder.FlightTime,
                    onSelect = { onOrderChange(DroneOrder.FlightTime(droneOrder.orderType)) })
            }

            Spacer(modifier = Modifier.height(4.dp))

            DefaultRadioButton(
                text = "Maximum Flight Height",
                selected = droneOrder is DroneOrder.MaximumFlightHeight,
                onSelect = { onOrderChange(DroneOrder.MaximumFlightHeight(droneOrder.orderType)) })

            Spacer(modifier = Modifier.height(4.dp))

            Row(modifier = Modifier.fillMaxWidth()) {

                Spacer(modifier = Modifier.width(8.dp))

                DefaultRadioButton(
                    text = "Ascending",
                    selected = droneOrder.orderType is OrderType.Ascending,
                    onSelect = { onOrderChange(droneOrder.copy(OrderType.Ascending)) })

                DefaultRadioButton(
                    text = "Descending",
                    selected = droneOrder.orderType is OrderType.Descending,
                    onSelect = { onOrderChange(droneOrder.copy(OrderType.Descending)) })
            }
        }
    }
}

