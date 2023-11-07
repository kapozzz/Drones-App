package com.example.vozdux.presenter.new_drone

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.vozdux.R
import com.example.vozdux.presenter.generalComponents.CustomAlertDialog
import com.example.vozdux.presenter.generalComponents.LoadingScreen
import com.example.vozdux.presenter.new_drone.components.NewDroneScreen
import com.example.vozdux.presenter.new_drone.components.PropertiesScreen

@Composable
fun NewDrone(
    viewModel: NewDroneViewModel,
    navController: NavController
) {
    val alertDialogState = remember {
        mutableStateOf(false)
    }

    BackHandler {
        alertDialogState.value = true
    }

    when (viewModel.screenState.value) {

        NewDroneScreenState.Loading -> {
            LoadingScreen()
        }

        NewDroneScreenState.Screen -> {
            NewDroneScreen(viewModel = viewModel, navController = navController)
        }

        NewDroneScreenState.PropertiesScreen -> {
            PropertiesScreen(
                items = if (viewModel.currentPage.value is CurrentPropertiesPage.LongDescription)
                    viewModel.currentDrone.value.longDescription else viewModel.currentDrone.value.otherProperties,
                viewModel = viewModel
            )
        }
    }

    if (alertDialogState.value) CustomAlertDialog(
        label = stringResource(id = R.string.are_you_sure),
        positive = {
            alertDialogState.value = false
            navController.popBackStack()
        },
        negative = { alertDialogState.value = false }
    )
}
