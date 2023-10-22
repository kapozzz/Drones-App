package com.example.vozdux.presenter.new_drone

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.vozdux.presenter.generalComponents.LoadingScreen
import com.example.vozdux.presenter.new_drone.components.NewDroneScreen


@Composable
fun NewDrone(
    viewModel: NewDroneViewModel,
    navController: NavController
) { StateHandler(state = viewModel.screenState.value, viewModel, navController) }

@Composable
fun StateHandler(
    state: NewDroneScreenState,
    viewModel: NewDroneViewModel,
    navController: NavController
) {

    when (state) {

        NewDroneScreenState.Loading -> {
            LoadingScreen()
        }

        NewDroneScreenState.Screen -> {
            NewDroneScreen(viewModel = viewModel, navController = navController)
        }
    }
}