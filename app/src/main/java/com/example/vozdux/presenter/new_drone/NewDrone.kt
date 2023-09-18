package com.example.vozdux.presenter.new_drone

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.vozdux.presenter.new_drone.components.CompositeElement

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NewDrone(
    viewModel: NewDroneViewModel
) {

    val sheetState = rememberModalBottomSheetState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 4.dp)
    ) {

        LazyColumn(
            contentPadding = PaddingValues(4.dp)
        ) {


            item {

                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "DroneConstructor",
                        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            item {

                Text(
                    text = "DroneName:",
                    style = MaterialTheme.typography.titleSmall
                )

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp, top = 4.dp),
                    maxLines = 2,
                    value = viewModel.currentDrone.value.name,
                    onValueChange = { newName ->
                        viewModel.onEvent(NewDroneScreenEvent.NameChanged(newName))
                    })
            }

            item {

                Text(
                    text = "ShortDescription:",
                    style = MaterialTheme.typography.titleSmall
                )

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp, top = 4.dp),
                    maxLines = 2,
                    value = viewModel.currentDrone.value.shortDescription,
                    onValueChange = { newShortDescription ->
                        viewModel.onEvent(
                            NewDroneScreenEvent.ShortDescriptionChanged(
                                newShortDescription
                            )
                        )
                    })
            }


            item {

                Text(
                    text = "CreationDate:",
                    style = MaterialTheme.typography.titleSmall
                )

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp, top = 4.dp),
                    maxLines = 2,
                    value = viewModel.currentDrone.value.creationDate,
                    onValueChange = { newCreationDate ->
                        viewModel.onEvent(NewDroneScreenEvent.CreationDateChanged(newCreationDate))
                    })
            }

            item {

                Text(
                    text = "Cost:",
                    style = MaterialTheme.typography.titleSmall
                )

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp, top = 4.dp),
                    maxLines = 2,
                    value = viewModel.currentDrone.value.cost,
                    onValueChange = { newCost ->
                        viewModel.onEvent(NewDroneScreenEvent.CostChanged(newCost))
                    })
            }

            stickyHeader {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = "LongDescription",
                        style = MaterialTheme.typography.titleSmall
                    )

                    Button(onClick = {
                        viewModel.onEvent(
                            NewDroneScreenEvent.BottomSheetStateChanged(
                                BottomSheetState.NewDescriptionHeadline
                            )
                        )
                    }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add new description headline"
                        )
                    }
                }
            }

            items(viewModel.currentDrone.value.longDescription) { element ->
                Log.i("STRANGEERROR", "longDesc element")
                CompositeElement(
                    element = element,
                    onElementChanged = { changedElement ->
                        viewModel.onEvent(
                            NewDroneScreenEvent.LongDescriptionElementChanged(
                                changedElement
                            )
                        )
                    }
                )
            }

            stickyHeader {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = "Properties",
                        style = MaterialTheme.typography.titleSmall
                    )

                    Button(onClick = {
                        viewModel.onEvent(
                            NewDroneScreenEvent.BottomSheetStateChanged(
                                BottomSheetState.NewProperty
                            )
                        )
                    }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add new property"
                        )
                    }
                }
            }

            items(viewModel.currentDrone.value.properties) { element ->
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Column {
                        Text(
                            text = element.name + ":"
                        )

                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = element.value, onValueChange = { newValue ->
                                val updatedElement = element.copy(
                                    value = newValue
                                )
                                viewModel.onEvent(
                                    NewDroneScreenEvent.PropertiesElementChanged(
                                        updatedElement
                                    )
                                )
                            })
                    }
                }
            }

//            item {
//                Text(text = "State:${viewModel.state.value.bottomSheetIsVisible}")
//            }
        }

        if (viewModel.bottomSheetState.value.bottomSheetIsVisible !is BottomSheetState.BottomSheetIsClosed) {
            Log.i("STRANGEERROR", "bottomSheet")
            ModalBottomSheet(
                onDismissRequest = {
                    viewModel.onEvent(NewDroneScreenEvent.BottomSheetStateChanged(BottomSheetState.BottomSheetIsClosed))
                },
                sheetState = sheetState
            ) {

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = if (
                            viewModel.bottomSheetState.value.bottomSheetIsVisible is BottomSheetState.NewProperty
                        ) "New property" else "New description headline",
                        style = MaterialTheme.typography.titleSmall
                    )

                    TextField(
                        value = if (viewModel.bottomSheetState.value.bottomSheetIsVisible is
                                    BottomSheetState.NewDescriptionHeadline
                        ) viewModel.bottomSheetState.value.bottomSheetContentState.descriptionField else
                            viewModel.bottomSheetState.value.bottomSheetContentState.propertyField,
                        onValueChange = { newText ->
                            if (viewModel.bottomSheetState.value.bottomSheetIsVisible is
                                        BottomSheetState.NewDescriptionHeadline
                            ) viewModel.onEvent(
                                NewDroneScreenEvent.BottomSheetDescriptionChanged(
                                    newText
                                )
                            ) else
                                viewModel.onEvent(
                                    NewDroneScreenEvent.BottomSheetPropertyChanged(
                                        newText
                                    )
                                )
                        })

                    Button(
                        modifier = Modifier.padding(bottom = 32.dp, top = 16.dp),
                        onClick = {
                            if (viewModel.bottomSheetState.value.bottomSheetIsVisible is
                                        BottomSheetState.NewDescriptionHeadline
                            ) viewModel.onEvent(NewDroneScreenEvent.DescriptionHeadlineNew()) else
                                viewModel.onEvent(NewDroneScreenEvent.PropertyNew())
                        }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                    }
                }

            }
        }
    }
}