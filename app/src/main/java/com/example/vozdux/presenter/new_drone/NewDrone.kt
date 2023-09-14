package com.example.vozdux.presenter.new_drone

import android.annotation.SuppressLint
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NewDrone(
    viewModel: NewDroneViewModel
) {

    Scaffold {

        LazyColumn {

            item {
                // DroneName
                TextField(value = "", onValueChange = {

                })
            }

        }
    }
}