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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.vozdux.domain.model.UploadDroneImage
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
                UploadDroneImage(
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
                                .align(Alignment.Center)
                            ,
                            state = pagerState
                        ) { page ->
                            AsyncImage(
                                model = viewModel.uris.value[page].uri,
                                contentDescription = null,
                                modifier = Modifier.fillMaxWidth().height(256.dp),
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
                            viewModel.onEvent(NewDroneScreenEvent.NameChanged(newName))
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
                                NewDroneScreenEvent.CreationDateChanged(
                                    newCreationDate
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
                            viewModel.onEvent(NewDroneScreenEvent.CostChanged(newCost))
                            viewModel.onEvent(NewDroneScreenEvent.ErrorInCost(newValue.isEmpty()))
                        },
                        isError = viewModel.fieldIsValid.value.costIsEmpty,
                        currentCurrencyChanged = { newCost ->
                            viewModel.onEvent(
                                NewDroneScreenEvent.CostChanged(
                                    newCost
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
                                NewDroneScreenEvent.ShortDescriptionChanged(
                                    newShortDescription
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
                                            CurrentPage.Properties
                                        )
                                    )
                                },
                                enabled = viewModel.currentPage.value !is CurrentPage.Properties
                            ) {
                                Text(text = "Properties")
                            }
                        }
                    }

                }

                if (viewModel.currentPage.value is CurrentPage.Description) {
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
                                        currentItem.name
                                    )
                                )
                            },
                            onCollapseClick = {
                                viewModel.onEvent(
                                    NewDroneScreenEvent.CurrentExpandedElementChanged(
                                        ""
                                    )
                                )
                            },
                            editItem = { item ->
                                viewModel.onEvent(NewDroneScreenEvent.BottomSheetSetId(currentItem.id))
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
                } else {
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
                        viewModel.currentDrone.value.properties,
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
                                        currentItem.name
                                    )
                                )
                            },
                            onCollapseClick = {
                                viewModel.onEvent(
                                    NewDroneScreenEvent.CurrentExpandedElementChanged(
                                        ""
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

                item {
                    androidx.compose.foundation.layout.Spacer(
                        modifier = androidx.compose.ui.Modifier
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
                    text = if (
                        viewModel.bottomSheetState.value.bottomSheetIsVisible is BottomSheetState.NewProperty
                    ) "New property" else "New description headline",
                    style = MaterialTheme.typography.titleSmall
                )

                OutlinedTextField(
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

                Button(
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp, top = 16.dp),
                    onClick = {
                        if (viewModel.bottomSheetState.value.bottomSheetIsVisible is
                                    BottomSheetState.NewDescriptionHeadline
                        ) viewModel.onEvent(NewDroneScreenEvent.DescriptionHeadlineNew) else
                            viewModel.onEvent(NewDroneScreenEvent.PropertyNew)
                    },
                    enabled = when (viewModel.bottomSheetState.value.bottomSheetIsVisible) {
                        is BottomSheetState.NewDescriptionHeadline -> {
                            viewModel.bottomSheetState.value.bottomSheetContentState.name.isNotEmpty()
                        }

                        is BottomSheetState.NewProperty -> {
                            viewModel.bottomSheetState.value.bottomSheetContentState.name.isNotEmpty()
                        }

                        is BottomSheetState.BottomSheetIsClosed -> {
                            false
                        }
                    }
                ) {
                    Text(text = "Save")
                }
            }


        }
    }
}