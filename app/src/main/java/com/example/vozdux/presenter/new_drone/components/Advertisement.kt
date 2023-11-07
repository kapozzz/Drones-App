package com.example.vozdux.presenter.new_drone.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun Advertisement() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(8.dp))
            .padding(8.dp)
            .size(200.dp),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = "https://podgornoe.kinel-cherkassy.ru/wp-content/uploads/2021/10/реклама-наркотических-средств-2.jpg" ,
            contentDescription = "ad"
        )
//        Text(
//            modifier = Modifier.fillMaxWidth().padding(8.dp).align(Alignment.Center),
//            text = "Реклама",
//            style = MaterialTheme.typography.titleLarge
//        )
    }
}