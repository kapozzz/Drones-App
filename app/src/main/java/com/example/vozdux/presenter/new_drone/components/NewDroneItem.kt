package com.example.vozdux.presenter.new_drone.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.vozdux.R
import com.example.vozdux.domain.model.drone.CompositeDroneElement
import com.example.vozdux.presenter.generalComponents.CustomAlertDialog
import com.example.vozdux.presenter.new_drone.CurrentPropertiesPage
import com.example.vozdux.presenter.new_drone.NewDroneScreenEvent

@Composable
fun NewDroneItem(
    currentItem: CompositeDroneElement,
    name: String,
    content: String,
    onExpandClick: () -> Unit,
    onCollapseClick: () -> Unit,
    editItem: (currentItem: CompositeDroneElement) -> Unit,
    modifier: Modifier = Modifier,
    isExpanded: Boolean = false,
    currentPage: CurrentPropertiesPage,
    onEvent: (event: NewDroneScreenEvent) -> Unit
) {

    val alertDialogState = remember {
        mutableStateOf(false)
    }

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
                    if (isExpanded) onCollapseClick()
                    else onExpandClick()
                }) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = name,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleSmall
                    )


                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        if (isExpanded) Button(
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary
                            ),
                            onClick = { alertDialogState.value = true }) {
                            Icon(
                                modifier = Modifier
                                    .size(20.dp),
                                imageVector = Icons.Default.Close,
                                contentDescription = stringResource(R.string.delete),
                                tint = MaterialTheme.colorScheme.onBackground,
                            )
                        }

                        if (isExpanded) Button(
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
                            imageVector = if (isExpanded)
                                Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = stringResource(R.string.show_expanded),
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }

                }
            }

            if (isExpanded) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = content,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        if (alertDialogState.value) {
            CustomAlertDialog(
                label = stringResource(id = R.string.are_you_sure),
                positive = { if (currentPage is CurrentPropertiesPage.LongDescription) {
                    onEvent(
                        NewDroneScreenEvent.DescriptionHeadlineDelete(
                            currentItem
                        )
                    )
                } else {
                    onEvent(
                        NewDroneScreenEvent.PropertyDelete(
                            currentItem
                        )
                    )
                } },
                negative = {alertDialogState.value = false}
            )
        }
    }
}