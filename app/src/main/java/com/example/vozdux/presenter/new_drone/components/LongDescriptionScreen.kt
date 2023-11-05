package com.example.vozdux.presenter.new_drone.components

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.vozdux.R
import com.example.vozdux.constants.EMPTY_STRING
import com.example.vozdux.domain.model.drone.CompositeDroneElement
import com.example.vozdux.presenter.new_drone.BottomSheetContentState
import com.example.vozdux.presenter.new_drone.CurrentPropertiesPage
import com.example.vozdux.presenter.new_drone.NewDroneScreenEvent
import com.example.vozdux.presenter.new_drone.NewDroneViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun PropertiesScreen(
    items: MutableList<CompositeDroneElement>,
    viewModel: NewDroneViewModel,
) {
    val currentPageName = stringResource(
        id = if (viewModel.currentPage.value is CurrentPropertiesPage.LongDescription)
            R.string.long_description
        else R.string.other_properties
    )
    BackHandler() {
        viewModel.onEvent(
            NewDroneScreenEvent.CurrentPageChanged(
                null
            )
        )
    }
    Scaffold(
        modifier = Modifier,
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(elevation = 4.dp),
                title = {
                    Text(
                        text = currentPageName,
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        viewModel.onEvent(
                            NewDroneScreenEvent.CurrentPageChanged(
                                null
                            )
                        )
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.search),
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    titleContentColor = MaterialTheme.colorScheme.secondary,
                    actionIconContentColor = MaterialTheme.colorScheme.secondary
                )
            )
        }
    ) { padding ->
        val bottomSheetState = viewModel.bottomSheetState.value
        val sheetState = rememberModalBottomSheetState()
        val currentExpandedElement = viewModel.currentExpandedElement.value
        val onEventLambda = { event: NewDroneScreenEvent ->
            viewModel.onEvent(event)
        }

        LazyColumn(
            modifier = Modifier.padding(padding)
        ) {
            stickyHeader {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = stringResource(R.string.add) + " " + currentPageName.lowercase())
                    IconButton(onClick = {
                        onEventLambda(NewDroneScreenEvent.BottomSheetStateChanged(true))
                    }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(R.string.add) + " " + currentPageName.lowercase()
                        )
                    }
                }
            }

            items(items = items, key = { currentItem -> currentItem.id })
            { currentItem ->
                NewDroneItem(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth(),
                    currentItem = currentItem,
                    name = currentItem.name,
                    content = currentItem.value,
                    isExpanded = currentExpandedElement == currentItem.id,
                    onExpandClick = {
                        onEventLambda(
                            NewDroneScreenEvent.CurrentExpandedElementChanged(
                                currentItem.id
                            )
                        )
                    },
                    onCollapseClick = {
                        onEventLambda(
                            NewDroneScreenEvent.CurrentExpandedElementChanged(
                                EMPTY_STRING
                            )
                        )
                    },
                    editItem = { item ->
                        onEventLambda(NewDroneScreenEvent.BottomSheetSetId(currentItem.id))
                        onEventLambda(NewDroneScreenEvent.BottomSheetStateChanged(true))
                        onEventLambda(
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
            item {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                )
            }
        }

        if (bottomSheetState.bottomSheetIsVisible) {
            ModalBottomSheet(
                onDismissRequest = {
                    viewModel.onEvent(NewDroneScreenEvent.BottomSheetStateChanged(false))
                },
                sheetState = sheetState
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    CustomTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        value = bottomSheetState.bottomSheetContentState.name,
                        onValueChanged = { newText ->
                            viewModel.onEvent(
                                NewDroneScreenEvent.BottomSheetContentChanged(
                                    bottomSheetState.bottomSheetContentState.copy(
                                        name = newText
                                    )
                                )
                            )
                        },
                        label = stringResource(R.string.name),
                        singleLine = true
                    )
                    CustomTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 8.dp),
                        value = viewModel.bottomSheetState.value.bottomSheetContentState.content,
                        onValueChanged = { newText ->
                            viewModel.onEvent(
                                NewDroneScreenEvent.BottomSheetContentChanged(
                                    viewModel.bottomSheetState.value.bottomSheetContentState.copy(
                                        content = newText
                                    )
                                )
                            )
                        },
                        label = stringResource(R.string.content),
                        maxLines = 8,
                        minLines = 4
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
    }
}