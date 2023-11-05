package com.example.vozdux.presenter.new_drone.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.vozdux.R
import com.example.vozdux.presenter.generalComponents.ImageScreen
import com.example.vozdux.presenter.new_drone.NewDroneScreenEvent
import com.example.vozdux.presenter.new_drone.NewDroneViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NewDroneScreen(
    viewModel: NewDroneViewModel,
    navController: NavController
) {
    val currentDrone = viewModel.currentDrone.value
    val currentImageToShow = viewModel.currentImageToShow.value
    val lazyListState = LazyListState()
    val onEventLambda = { event: NewDroneScreenEvent -> viewModel.onEvent(event) }
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(NewDroneScreenEvent.SaveDrone)
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = stringResource(R.string.save),
                    tint = Color.White,
                )
            }
        },
        topBar = {
            ConstructorTopBar {
                navController.popBackStack()
            }
        }
    ) { padding ->
        Surface(
            modifier = Modifier.fillMaxSize().padding(8.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                state = lazyListState
            ) {

                item {
                    AddPhotoButton(
                        onEvent = onEventLambda,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }

                item {
                    ImagesEditHandler(
                        images = currentDrone.images,
                        onEvent = onEventLambda,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item {
                    CustomTextField(
                        label = stringResource(id = R.string.name_of_drone),
                        value = currentDrone.name,
                        onValueChanged = { newName ->
                            onEventLambda(
                                NewDroneScreenEvent.FieldChanged(
                                    currentDrone.copy(name = newName)
                                )
                            )
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp, top = 4.dp)
                    )
                }

                item {
                    CustomTextField(
                        label = stringResource(id = R.string.creation_date),
                        value = currentDrone.creationDate,
                        onValueChanged = { newCreationDate ->
                            onEventLambda(
                                NewDroneScreenEvent.FieldChanged(
                                    currentDrone.copy(creationDate = newCreationDate)
                                )
                            )
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp, top = 4.dp)
                    )
                }

                item {
                    NewDroneCost(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp, top = 4.dp),
                        value = currentDrone.cost,
                        onValueChanged = { newValue ->
                            val newCost = currentDrone.cost.copy(value = newValue)
                            onEventLambda(
                                NewDroneScreenEvent.FieldChanged(
                                    currentDrone.copy(cost = newCost)
                                )
                            )
                        },
                        currentCurrencyChanged = { newCost ->
                            onEventLambda(
                                NewDroneScreenEvent.FieldChanged(
                                    currentDrone.copy(cost = newCost)
                                )
                            )
                        }
                    )
                }

                item {
                    CustomTextField(
                        label = stringResource(R.string.country),
                        value = currentDrone.country,
                        onValueChanged = { newCountry ->
                            onEventLambda(
                                NewDroneScreenEvent.FieldChanged(
                                    currentDrone.copy(
                                        country = newCountry
                                    )
                                )
                            )
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp, top = 4.dp)
                    )
                }

                item {
                    CustomTextField(
                        label = stringResource(id = R.string.short_description),
                        value = currentDrone.shortDescription,
                        onValueChanged = { newShortDescription ->
                            onEventLambda(
                                NewDroneScreenEvent.FieldChanged(
                                    currentDrone.copy(shortDescription = newShortDescription)
                                )
                            )
                        },
                        minLines = 4,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp, top = 4.dp)
                    )
                }

                item {
                    CustomTextField(
                        label = stringResource(R.string.battery),
                        value = currentDrone.battery.toString(),
                        onValueChanged = { value ->
                            onEventLambda(
                                NewDroneScreenEvent.FieldChanged(
                                    currentDrone.copy(battery = value.filter { it.isDigit() }.toInt())
                                )
                            )
                        },
                        minLines = 4,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp, top = 4.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    )
                }

                item {
                    PropertiesRow(
                        modifier = Modifier.fillMaxWidth(),
                        onEvent = onEventLambda
                    )
                }

                item {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                    )
                }
            }
        }
    }
    if (currentImageToShow != null) ImageScreen(
        image = currentImageToShow,
        onCloseClick = { viewModel.onEvent(NewDroneScreenEvent.ShowImage(null)) }
    )
}

