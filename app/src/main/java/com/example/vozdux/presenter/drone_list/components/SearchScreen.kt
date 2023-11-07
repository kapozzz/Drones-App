package com.example.vozdux.presenter.drone_list.components

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.vozdux.R
import com.example.vozdux.constants.EMPTY_STRING
import com.example.vozdux.domain.model.drone.Drone
import com.example.vozdux.presenter.drone_list.DroneListEvent
import com.example.vozdux.presenter.drone_list.DronesListViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchScreen(
    query: String,
    drones: List<Drone>,
    onQueryChange: (query: String) -> Unit,
    onItemClick: (id: String) -> Unit,
    viewModel: DronesListViewModel
) {

    val filterIsVisible = viewModel.filterIsVisible.value
    val lazyListState = LazyListState()

    BackHandler() {
        viewModel.onEvent(DroneListEvent.CloseSearchScreen)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(padding),
            state = lazyListState
        ) {



            stickyHeader {
                Column() {
                    TextField(
                        value = query,
                        onValueChange = { query ->
                            onQueryChange.invoke(query)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .shadow(8.dp, RoundedCornerShape(16.dp)),
                        colors = TextFieldDefaults.colors(
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent,
                            unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                            focusedContainerColor = MaterialTheme.colorScheme.secondary
                        ),
                        leadingIcon = {
                            IconButton(onClick = {
                                viewModel.onEvent(DroneListEvent.CloseSearchScreen)
                            }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBackIosNew,
                                    contentDescription = stringResource(R.string.close)
                                )
                            }
                        },
                        trailingIcon = {
                            IconButton(onClick = {
                                if (query.isNotEmpty()) onQueryChange(EMPTY_STRING)
                            }) {
                                Icon(
                                    imageVector = if (query.isNotEmpty()) Icons.Default.Close else Icons.Default.Search,
                                    contentDescription = stringResource(R.string.search_clear_query)
                                )
                            }
                        },
                        placeholder = {
                             Text(text = "Search...")         
                        },
                        maxLines = 1
                    )


                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .shadow(
                                elevation = 8.dp,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable { viewModel.onEvent(DroneListEvent.FilterOnClick) }
                            .background(MaterialTheme.colorScheme.secondary)
                        ,
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(6.dp)
                            ,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (filterIsVisible) stringResource(R.string.close_filter) else
                                    stringResource(R.string.show_filter),
                                style = MaterialTheme.typography.titleSmall
                            )
                            Icon(
                                imageVector =
                                if (filterIsVisible) Icons.Default.ArrowDropDown else Icons.Default.ArrowDropUp,
                                contentDescription =
                                if (filterIsVisible) stringResource(R.string.close_filter) else
                                    stringResource(R.string.show_filter)
                            )
                        }
                    }

                    AnimatedVisibility(
                        visible = filterIsVisible,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        OrderSection(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                            onOrderChange = { order ->
                                viewModel.onEvent(DroneListEvent.NewOrder(order))
                            },
                            droneOrder = viewModel.order.value
                        )
                    }
                }


            }

            items(
                items = drones,
                key = { drone ->
                    drone.id
                }
            ) { element ->
                DroneItem(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp),
                    element = element,
                    onClick = { onItemClick(element.id) }
                )
            }
        }
    }
}
