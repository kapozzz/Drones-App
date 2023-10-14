package com.example.vozdux.presenter.drone_list

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.vozdux.presenter.drone_list.components.DroneItem
import com.example.vozdux.presenter.util.Screen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DronesList(
    viewModel: DronesListViewModel,
    navController: NavController
) {

    Scaffold(
        floatingActionButton = {
            Button(onClick = {
                navController.navigate(Screen.NewDroneScreen.route)
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add new drone")
            }
        }
    ) {
        LazyColumn {
            items(viewModel.state.value.drones) { element ->
                DroneItem(modifier = Modifier.padding(8.dp), element = element, onClick = {
                    navController.navigate(Screen.NewDroneScreen.route + "?drone=${element.drone.id}")
                })
            }
        }
    }
}