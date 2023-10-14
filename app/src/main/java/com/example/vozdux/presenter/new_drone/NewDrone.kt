package com.example.vozdux.presenter.new_drone

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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.vozdux.constants.EMPTY_STRING
import com.example.vozdux.domain.model.drone.Image
import com.example.vozdux.presenter.new_drone.components.MainPropertyItem
import com.example.vozdux.presenter.new_drone.components.NewDroneCost
import com.example.vozdux.presenter.new_drone.components.NewDroneCreationDate
import com.example.vozdux.presenter.new_drone.components.NewDroneHeader
import com.example.vozdux.presenter.new_drone.components.NewDroneItem
import com.example.vozdux.presenter.new_drone.components.NewDroneName
import com.example.vozdux.presenter.new_drone.components.NewDroneSecondaryHeader
import com.example.vozdux.presenter.new_drone.components.NewDroneShortDescription

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NewDrone(
    viewModel: NewDroneViewModel
) {

    val sheetState = rememberModalBottomSheetState()
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uris ->
            val changedUris = uris.map { newUri ->
                Image(
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
            .padding(horizontal = 8.dp),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(NewDroneScreenEvent.SaveDrone)
            }) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = "Save"
                )
            }
        }
    ) {

        Column {
            LazyColumn(
                contentPadding = PaddingValues(4.dp)
            ) {

                item {
                    NewDroneHeader(
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            photoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddAPhoto,
                            contentDescription = "Add photo"
                        )
                    }
                }

                item {

                    val pagerState = rememberPagerState(pageCount = {
                        viewModel.uris.value.size
                    })

                    if (viewModel.uris.value.isNotEmpty()) Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        HorizontalPager(
                            modifier = Modifier
                                .padding(vertical = 16.dp)
                                .fillMaxWidth()
                                .align(Alignment.Center),
                            state = pagerState
                        ) { page ->
                            AsyncImage(
                                model = viewModel.uris.value[page].uri,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(256.dp),
                                contentScale = ContentScale.FillWidth
                            )
                        }
                    }
                }

                item {
                    NewDroneName(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp, top = 4.dp)
                            .background(MaterialTheme.colorScheme.background),
                        value = viewModel.currentDrone.value.name,
                        onValueChange = { newName ->
                            viewModel.onEvent(
                                NewDroneScreenEvent.FieldChanged(
                                    viewModel.currentDrone.value.copy(
                                        name = newName
                                    )
                                )
                            )
                            viewModel.onEvent(
                                NewDroneScreenEvent.ErrorInName(newName.isEmpty())
                            )
                        },
                        isError = viewModel.fieldIsValid.value.nameIsEmpty
                    )
                }

                item {

                    NewDroneCreationDate(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp, top = 4.dp),
                        value = viewModel.currentDrone.value.creationDate,
                        onValueChange = { newCreationDate ->
                            viewModel.onEvent(
                                NewDroneScreenEvent.FieldChanged(
                                    viewModel.currentDrone.value.copy(
                                        creationDate = newCreationDate
                                    )
                                )
                            )
                            viewModel.onEvent(
                                NewDroneScreenEvent.ErrorInCreationDate(
                                    newCreationDate.isEmpty()
                                )
                            )
                        },
                        isError = viewModel.fieldIsValid.value.creationDateIsEmpty
                    )

//                    val year: Int
//                    val month: Int
//                    val day: Int
//
//                    val calendar = Calendar.getInstance()
//                    year = calendar.get(Calendar.YEAR)
//                    month = calendar.get(Calendar.MONTH)
//                    day = calendar.get(Calendar.DAY_OF_MONTH)
//
//                    val date = remember { mutableStateOf("") }
//                    val datePickerDialog = DatePickerDialog(
//
//                    )

                }

                item {

                    NewDroneCost(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp, top = 4.dp),
                        value = viewModel.currentDrone.value.cost,
                        onValueChange = { newValue ->
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
                            viewModel.onEvent(NewDroneScreenEvent.ErrorInCost(newValue.isEmpty()))
                        },
                        isError = viewModel.fieldIsValid.value.costIsEmpty,
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

                    NewDroneShortDescription(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp, top = 4.dp),
                        value = viewModel.currentDrone.value.shortDescription,
                        onValueChange = { newShortDescription ->
                            viewModel.onEvent(
                                NewDroneScreenEvent.FieldChanged(
                                    viewModel.currentDrone.value.copy(
                                        shortDescription = newShortDescription
                                    )
                                )
                            )
                            viewModel.onEvent(
                                NewDroneScreenEvent.ErrorInShortDescription(
                                    newShortDescription.isEmpty()
                                )
                            )
                        },
                        isError = viewModel.fieldIsValid.value.shortDescriptionIsEmpty
                    )
                }

                item {

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 8.dp),
                        shape = RoundedCornerShape(8.dp),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 4.dp
                        )
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
                                Text(text = "Description")
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
                                Text(text = "General properties")
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
                                Text(text = "Other properties")
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
                                    .background(androidx.compose.material3.MaterialTheme.colorScheme.background),
                                onAddClick = {
                                    viewModel.onEvent(
                                        NewDroneScreenEvent.BottomSheetStateChanged(
                                            BottomSheetState.NewDescriptionHeadline
                                        )
                                    )
                                },
                                value = "Long description"
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
                                currentExpandedElement = viewModel.currentExpandedElement.value,
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
                                        NewDroneScreenEvent.BottomSheetStateChanged(
                                            BottomSheetState.NewDescriptionHeadline
                                        )
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

                        items(viewModel.currentDrone.value.mainProperties.toList()) {
                            MainPropertyItem(
                                modifier = Modifier
                                    .padding(bottom = 8.dp)
                                    .fillMaxWidth(),
                                currentItem = it,
                                currentExpandedElement = viewModel.currentExpandedElement.value,
                                onExpandClick = {
                                    viewModel.onEvent(
                                        NewDroneScreenEvent.CurrentExpandedElementChanged(
                                            it.id
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
                                        NewDroneScreenEvent.BottomSheetStateChanged(
                                            BottomSheetState.EditMainProperty
                                        )
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
                                        NewDroneScreenEvent.BottomSheetStateChanged(
                                            BottomSheetState.NewProperty
                                        )
                                    )
                                },
                                value = "Add new property"
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
                                currentExpandedElement = viewModel.currentExpandedElement.value,
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
                                        NewDroneScreenEvent.BottomSheetStateChanged(
                                            BottomSheetState.NewProperty
                                        )
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
                    androidx.compose.foundation.layout.Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                    )
                }
            }
        }

    }

    if (viewModel.bottomSheetState.value.bottomSheetIsVisible !is BottomSheetState.BottomSheetIsClosed) {

        ModalBottomSheet(
            onDismissRequest = {
                viewModel.onEvent(NewDroneScreenEvent.BottomSheetStateChanged(BottomSheetState.BottomSheetIsClosed))
            },
            sheetState = sheetState
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {

                Text(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .align(Alignment.Start),
                    text = when (viewModel.bottomSheetState.value.bottomSheetIsVisible) {
                        is BottomSheetState.NewProperty -> {
                            "New property"
                        }

                        is BottomSheetState.EditMainProperty -> {
                            viewModel.bottomSheetState.value.bottomSheetContentState.name
                        }

                        is BottomSheetState.NewDescriptionHeadline -> {
                            "New description headline"
                        }

                        else -> {
                            " "
                        }
                    }
                )

                if (viewModel.bottomSheetState.value.bottomSheetIsVisible !is BottomSheetState.EditMainProperty) OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    value = viewModel.bottomSheetState.value.bottomSheetContentState.name,
                    onValueChange = { newText ->
                        viewModel.onEvent(
                            NewDroneScreenEvent.BottomSheetContentChanged(
                                viewModel.bottomSheetState.value.bottomSheetContentState.copy(
                                    name = newText
                                )
                            )
                        )
                    },
                    singleLine = true
                )

                when (viewModel.bottomSheetState.value.bottomSheetIsVisible) {

                    is BottomSheetState.EditMainProperty -> {
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 8.dp),
                            value = viewModel.bottomSheetState.value.bottomSheetContentState.content,
                            onValueChange = { newText ->
                                val filteredText = newText.filter { it.isDigit() }
                                viewModel.onEvent(
                                    NewDroneScreenEvent.BottomSheetContentChanged(
                                        viewModel.bottomSheetState.value.bottomSheetContentState.copy(
                                            content = filteredText
                                        )
                                    )
                                )
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            maxLines = 8
                        )
                    }

                    else -> {
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 8.dp),
                            value = viewModel.bottomSheetState.value.bottomSheetContentState.content,
                            onValueChange = { newText ->
                                viewModel.onEvent(
                                    NewDroneScreenEvent.BottomSheetContentChanged(
                                        viewModel.bottomSheetState.value.bottomSheetContentState.copy(
                                            content = newText
                                        )
                                    )
                                )
                            },
                            minLines = 8
                        )
                    }
                }

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp, top = 16.dp),
                    onClick = {
                        when (viewModel.bottomSheetState.value.bottomSheetIsVisible) {

                            is BottomSheetState.NewDescriptionHeadline -> {
                                viewModel.onEvent(NewDroneScreenEvent.DescriptionHeadlineNew)
                            }

                            is BottomSheetState.EditMainProperty -> {
                                viewModel.onEvent(NewDroneScreenEvent.MainPropertiesChanged)
                            }

                            is BottomSheetState.NewProperty -> {
                                viewModel.onEvent(NewDroneScreenEvent.PropertyNew)
                            }

                            else -> {
                                throw IllegalStateException("wrong bottom sheet state")
                            }
                        }
                    },
                    enabled = when (viewModel.bottomSheetState.value.bottomSheetIsVisible) {

                        is BottomSheetState.BottomSheetIsClosed -> {
                            false
                        }

                        else -> {
                            viewModel.bottomSheetState.value.bottomSheetContentState.name.isNotEmpty()
                        }
                    }
                ) {
                    Text(text = "Save")
                }
            }
        }
    }
}