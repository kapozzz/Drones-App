package com.example.vozdux.presenter.drone_list

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.vozdux.presenter.drone_list.DroneListScreenState.*
import com.example.vozdux.presenter.drone_list.components.DronesListScreen
import com.example.vozdux.presenter.generalComponents.LoadingScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DronesList(
    viewModel: DronesListViewModel,
    navController: NavController
) {

    DronesListScreen(
        viewModel = viewModel,
        navController = navController
    )

//    CurrentScreen(
//        state = viewModel.screenState.value,
//        viewModel = viewModel,
//        navController = navController
//    )
}

@Composable
fun CurrentScreen(
    state: DroneListScreenState,
    viewModel: DronesListViewModel,
    navController: NavController
) {
    when (state) {

        isLoading -> {
            LoadingScreen()
        }

        isVisible -> {
            DronesListScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
    }
}