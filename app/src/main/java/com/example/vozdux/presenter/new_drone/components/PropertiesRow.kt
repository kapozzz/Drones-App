package com.example.vozdux.presenter.new_drone.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.vozdux.R
import com.example.vozdux.presenter.new_drone.CurrentPropertiesPage
import com.example.vozdux.presenter.new_drone.NewDroneScreenEvent

@Composable
fun PropertiesRow(
    modifier: Modifier = Modifier,
    onEvent: (event: NewDroneScreenEvent) -> Unit,
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(4.dp),
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = {
                    onEvent(
                        NewDroneScreenEvent.CurrentPageChanged(CurrentPropertiesPage.LongDescription)
                    )
                }
            ) {
                Text(text = stringResource(R.string.description))
            }
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )
            TextButton(
                onClick = {
                    onEvent(
                        NewDroneScreenEvent.CurrentPageChanged(CurrentPropertiesPage.OtherProperties)
                    )
                }
            ) {
                Text(text = stringResource(R.string.other_properties))
            }
        }
    }
}