package com.example.vozdux.presenter.drone_list

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.vozdux.presenter.drone_list.DroneListScreenState.Loading
import com.example.vozdux.presenter.drone_list.DroneListScreenState.Screen
import com.example.vozdux.presenter.drone_list.DroneListScreenState.Search
import com.example.vozdux.presenter.drone_list.components.DronesListScreen
import com.example.vozdux.presenter.drone_list.components.SearchScreen
import com.example.vozdux.presenter.generalComponents.LoadingScreen

@Composable
fun DronesList(
    viewModel: DronesListViewModel,
    navController: NavController
) {

    when (viewModel.screenState.value) {
        Loading -> {
            LoadingScreen()
        }

        Screen -> {
            DronesListScreen(
                viewModel = viewModel,
                navController = navController
            )
        }

        Search -> {
            SearchScreen(
                query = viewModel.searchQuery.value,
                drones = viewModel.drones.value,
                onQueryChange = { query ->
                    viewModel.onEvent(DroneListEvent.QueryChanged(query))
                },
                onItemClick = {

                },
                viewModel = viewModel
            )
        }
    }
}
