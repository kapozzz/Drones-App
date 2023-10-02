package com.example.vozdux.presenter.drone_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vozdux.data.RepositoryImpl

//@Preview
//@Composable
//fun Preview() {
//    DroneDetailsScreen(
//        viewModel = DroneDetailsScreenViewModel(repository = RepositoryImpl(), droneId = "adajlk")
//    )
//}

@Composable
fun DroneDetailsScreen(
    viewModel: DroneDetailsScreenViewModel
) {

    Box(
       modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(230.dp)
                    .padding(8.dp),
                imageVector = Icons.Default.Clear,
                contentDescription = viewModel.state.value.drone!!.name + "image")

            Text(
                text = viewModel.state.value.drone!!.name + "image",
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}
