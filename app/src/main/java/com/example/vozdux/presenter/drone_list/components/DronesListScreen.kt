package com.example.vozdux.presenter.drone_list.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.vozdux.R
import com.example.vozdux.presenter.drone_list.DronesListViewModel
import com.example.vozdux.presenter.util.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun DronesListScreen(
    viewModel: DronesListViewModel,
    navController: NavController
) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { ListScreenTopBar(onSearchIconClicked = {}, scrollBehavior) },
        floatingActionButton = {
            Button(onClick = {
                navController.navigate(Screen.NewDroneScreen.route)
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_new_drone)
                )
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
        ) {
            items(
                items = viewModel.state.value.drones,
                key = { item ->
                    item.drone.id
                }
            ) { element ->

                DroneItem(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp),
                    element = element,
                    onClick = {
                        navController.navigate(Screen.NewDroneScreen.route + "?drone=${element.drone.id}")
                    })
            }
        }
    }
}