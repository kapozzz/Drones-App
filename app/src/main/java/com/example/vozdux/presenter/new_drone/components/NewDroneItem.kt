package com.example.vozdux.presenter.new_drone.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.vozdux.R
import com.example.vozdux.domain.model.drone.CompositeDroneElement

@Composable
fun NewDroneItem(
    currentItem: CompositeDroneElement,
    currentExpandedElement: String,
    onExpandClick: () -> Unit,
    onCollapseClick: () -> Unit,
    editItem: (currentItem: CompositeDroneElement) -> Unit,
    modifier: Modifier = Modifier
) {

    val isVisible =
        currentExpandedElement == currentItem.id

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(4.dp),
        shadowElevation = 2.dp
    ) {
        Column {

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    if (isVisible) onCollapseClick()
                    else onExpandClick()
                }) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = currentItem.name,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleSmall
                    )


                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        if (isVisible) Button(
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary
                            ),
                            onClick = {
                                editItem(currentItem)
                            }) {
                            Icon(
                                modifier = Modifier
                                    .size(20.dp),
                                imageVector = Icons.Default.Edit,
                                contentDescription = stringResource(R.string.edit),
                                tint = MaterialTheme.colorScheme.onBackground,
                            )
                        }

                        Icon(
                            imageVector = if (isVisible)
                                Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = stringResource(R.string.show_expanded),
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }

                }
            }

            if (isVisible) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = currentItem.value,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}