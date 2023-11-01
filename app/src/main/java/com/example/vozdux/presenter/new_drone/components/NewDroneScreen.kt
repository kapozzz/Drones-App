package com.example.vozdux.presenter.new_drone.components

import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.vozdux.R
import com.example.vozdux.constants.EMPTY_STRING
import com.example.vozdux.domain.model.drone.UriImage
import com.example.vozdux.presenter.generalComponents.ImageScreen
import com.example.vozdux.presenter.new_drone.BottomSheetContentState
import com.example.vozdux.presenter.new_drone.CurrentPage
import com.example.vozdux.presenter.new_drone.NewDroneScreenEvent
import com.example.vozdux.presenter.new_drone.NewDroneViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NewDroneScreen(
    viewModel: NewDroneViewModel,
    navController: NavController
) {
    val lazyListState = rememberLazyListState()
    val sheetState = rememberModalBottomSheetState()
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uris ->
            val changedUris = uris.map { newUri ->
                UriImage(
                    uri = newUri
                )
            }
            viewModel.onEvent(
                NewDroneScreenEvent.UrisChanged(
                    changedUris
                )
            )
        }
    )

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

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.padding(padding),
                contentPadding = PaddingValues(4.dp),
                state = lazyListState
            ) {

                item {
                    Button(
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier
                            .fillMaxWidth(),
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
                item {
                    val images = viewModel.currentDrone.value.images
                    if (images.isNotEmpty()) ImagesEditHandler(
                        images = images,
                        onItemClick = { image ->
                           viewModel.onEvent(NewDroneScreenEvent.ShowImage(image))
                        },
                        onDeleteClick = { image ->
                            viewModel.onEvent(
                                NewDroneScreenEvent.DeleteUriImage(
                                    image
                                )
                            )
                        }
                    )
                }
                item {
                    CustomTextField(
                        label = stringResource(id = R.string.name_of_drone),
                        value = viewModel.currentDrone.value.name,
                        onValueChanged = { newName ->
                            viewModel.onEvent(
                                NewDroneScreenEvent.FieldChanged(
                                    viewModel.currentDrone.value.copy(
                                        name = newName
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
                        label = stringResource(id = R.string.creation_date),
                        value = viewModel.currentDrone.value.creationDate,
                        onValueChanged = { newCreationDate ->
                            viewModel.onEvent(
                                NewDroneScreenEvent.FieldChanged(
                                    viewModel.currentDrone.value.copy(
                                        creationDate = newCreationDate
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
                    NewDroneCost(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp, top = 4.dp),
                        value = viewModel.currentDrone.value.cost,
                        onValueChanged = { newValue ->
                            val newCost = viewModel.currentDrone.value.cost.copy(
                                value = newValue
                            )
                            viewModel.onEvent(
                                NewDroneScreenEvent.FieldChanged(
                                    viewModel.currentDrone.value.copy(
                                        cost = newCost
                                    )
                                )
                            )
                        },
                        currentCurrencyChanged = { newCost ->
                            viewModel.onEvent(
                                NewDroneScreenEvent.FieldChanged(
                                    viewModel.currentDrone.value.copy(
                                        cost = newCost
                                    )
                                )
                            )
                        }
                    )
                }
                item {
                    CustomTextField(
                        label = stringResource(R.string.country),
                        value = viewModel.currentDrone.value.country,
                        onValueChanged = { newCountry ->
                            viewModel.onEvent(
                                NewDroneScreenEvent.FieldChanged(
                                    viewModel.currentDrone.value.copy(
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
                        value = viewModel.currentDrone.value.shortDescription,
                        onValueChanged = { newShortDescription ->
                            viewModel.onEvent(
                                NewDroneScreenEvent.FieldChanged(
                                    viewModel.currentDrone.value.copy(
                                        shortDescription = newShortDescription
                                    )
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
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 8.dp),
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
                                    viewModel.onEvent(
                                        NewDroneScreenEvent.CurrentPageChanged(
                                            CurrentPage.Description
                                        )
                                    )
                                },
                                enabled = viewModel.currentPage.value !is CurrentPage.Description
                            ) {
                                Text(text = stringResource(R.string.description))
                            }
                            Divider(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(1.dp)
                            )
                            TextButton(
                                onClick = {
                                    viewModel.onEvent(
                                        NewDroneScreenEvent.CurrentPageChanged(
                                            CurrentPage.MainProperties
                                        )
                                    )
                                },
                                enabled = viewModel.currentPage.value !is CurrentPage.MainProperties
                            ) {
                                Text(text = stringResource(R.string.general_properties))
                            }
                            Divider(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(1.dp)
                            )
                            TextButton(
                                onClick = {
                                    viewModel.onEvent(
                                        NewDroneScreenEvent.CurrentPageChanged(
                                            CurrentPage.Properties
                                        )
                                    )
                                },
                                enabled = viewModel.currentPage.value !is CurrentPage.Properties
                            ) {
                                Text(text = stringResource(R.string.other_properties))
                            }
                        }
                    }
                }
                when (viewModel.currentPage.value) {
                    is CurrentPage.Description -> {
                        stickyHeader {

                            NewDroneSecondaryHeader(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.background),
                                onAddClick = {
                                    viewModel.onEvent(
                                        NewDroneScreenEvent.BottomSheetStateChanged(true)
                                    )
                                },
                                value = stringResource(R.string.long_description)
                            )
                        }
                        items(
                            viewModel.currentDrone.value.longDescription,
                            key = { currentItem ->
                                currentItem.id
                            }
                        ) { currentItem ->
                            NewDroneItem(
                                modifier = Modifier
                                    .padding(bottom = 8.dp)
                                    .fillMaxWidth(),
                                currentItem = currentItem,
                                name = currentItem.name,
                                content = currentItem.value,
                                isExpanded = viewModel.currentExpandedElement.value == currentItem.id,
                                onExpandClick = {
                                    viewModel.onEvent(
                                        NewDroneScreenEvent.CurrentExpandedElementChanged(
                                            currentItem.id
                                        )
                                    )
                                },
                                onCollapseClick = {
                                    viewModel.onEvent(
                                        NewDroneScreenEvent.CurrentExpandedElementChanged(
                                            EMPTY_STRING
                                        )
                                    )
                                },
                                editItem = { item ->
                                    viewModel.onEvent(
                                        NewDroneScreenEvent.BottomSheetSetId(
                                            currentItem.id
                                        )
                                    )
                                    viewModel.onEvent(
                                        NewDroneScreenEvent.BottomSheetStateChanged(true)
                                    )
                                    viewModel.onEvent(
                                        NewDroneScreenEvent.BottomSheetContentChanged(
                                            content = BottomSheetContentState(
                                                name = item.name,
                                                content = item.value,
                                                currentId = item.id
                                            )
                                        )
                                    )
                                })
                        }
                    }

                    is CurrentPage.MainProperties -> {
                        items(viewModel.currentDrone.value.mainProperties.toList()) { currentItem ->
                            NewDroneItem(
                                modifier = Modifier
                                    .padding(bottom = 8.dp)
                                    .fillMaxWidth(),
                                currentItem = currentItem,
                                isExpanded = viewModel.currentExpandedElement.value == currentItem.id,
                                name = currentItem.name,
                                content = currentItem.value.toString(),
                                onExpandClick = {
                                    viewModel.onEvent(
                                        NewDroneScreenEvent.CurrentExpandedElementChanged(
                                            currentItem.id
                                        )
                                    )
                                },
                                onCollapseClick = {
                                    viewModel.onEvent(
                                        NewDroneScreenEvent.CurrentExpandedElementChanged(
                                            EMPTY_STRING
                                        )
                                    )
                                },
                                editItem = { element ->
                                    viewModel.onEvent(
                                        NewDroneScreenEvent.BottomSheetStateChanged(true)
                                    )
                                    viewModel.onEvent(
                                        NewDroneScreenEvent.BottomSheetContentChanged(
                                            content = BottomSheetContentState(
                                                name = element.name,
                                                content = element.value.toString()
                                            )
                                        )
                                    )
                                }
                            )
                        }
                    }

                    is CurrentPage.Properties -> {
                        stickyHeader {
                            NewDroneSecondaryHeader(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.background),
                                onAddClick = {
                                    viewModel.onEvent(
                                        NewDroneScreenEvent.BottomSheetStateChanged(true)
                                    )
                                },
                                value = stringResource(R.string.add_new_property)
                            )
                        }
                        items(
                            viewModel.currentDrone.value.otherProperties,
                            key = { currentItem ->
                                currentItem.id
                            }
                        ) { currentItem ->
                            NewDroneItem(
                                modifier = Modifier
                                    .padding(bottom = 8.dp)
                                    .fillMaxWidth(),
                                currentItem = currentItem,
                                isExpanded = viewModel.currentExpandedElement.value == currentItem.id,
                                name = currentItem.name,
                                content = currentItem.value,
                                onExpandClick = {
                                    viewModel.onEvent(
                                        NewDroneScreenEvent.CurrentExpandedElementChanged(
                                            currentItem.id
                                        )
                                    )
                                },
                                onCollapseClick = {
                                    viewModel.onEvent(
                                        NewDroneScreenEvent.CurrentExpandedElementChanged(
                                            EMPTY_STRING
                                        )
                                    )
                                },
                                editItem = { item ->
                                    viewModel.onEvent(
                                        NewDroneScreenEvent.BottomSheetStateChanged(true)
                                    )
                                    viewModel.onEvent(
                                        NewDroneScreenEvent.BottomSheetContentChanged(
                                            content = BottomSheetContentState(
                                                name = item.name,
                                                content = item.value,
                                                currentId = item.id
                                            )
                                        )
                                    )
                                })
                        }
                    }
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
        if (viewModel.bottomSheetState.value.bottomSheetIsVisible) {
            ModalBottomSheet(
                onDismissRequest = {
                    viewModel.onEvent(NewDroneScreenEvent.BottomSheetStateChanged(false))
                },
                sheetState = sheetState
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    val bottomSheetState = viewModel.bottomSheetState.value
                    val isMainProperty = viewModel.currentPage.value is CurrentPage.MainProperties
                    CustomTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        value = bottomSheetState.bottomSheetContentState.name,
                        onValueChanged = { newText ->
                            viewModel.onEvent(
                                NewDroneScreenEvent.BottomSheetContentChanged(
                                    viewModel.bottomSheetState.value.bottomSheetContentState.copy(
                                        name = newText
                                    )
                                )
                            )
                        },
                        label = stringResource(R.string.name),
                        readOnly = isMainProperty,
                        singleLine = true
                    )
                    CustomTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 8.dp),
                        value = viewModel.bottomSheetState.value.bottomSheetContentState.content,
                        onValueChanged = { newText ->
                            val filteredText =
                                if (isMainProperty) newText.filter { it.isDigit() } else newText
                            viewModel.onEvent(
                                NewDroneScreenEvent.BottomSheetContentChanged(
                                    viewModel.bottomSheetState.value.bottomSheetContentState.copy(
                                        content = filteredText
                                    )
                                )
                            )
                        },
                        label = stringResource(R.string.content),
                        maxLines = 8,
                        minLines = 4,
                        keyboardOptions = KeyboardOptions(keyboardType = if (isMainProperty) KeyboardType.Number else KeyboardType.Text)
                    )
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 32.dp, top = 16.dp),
                        onClick = { viewModel.onEvent(NewDroneScreenEvent.SaveBottomSheet) },
                        enabled = bottomSheetState.bottomSheetContentState.name.isNotEmpty()
                    ) { Text(text = stringResource(id = R.string.save)) }
                }
            }
        }

        if (viewModel.currentImageToShow.value != null) ImageScreen(
            image = viewModel.currentImageToShow.value!!,
            onCloseClick = { viewModel.onEvent(NewDroneScreenEvent.ShowImage(null)) }
        )

    }
}