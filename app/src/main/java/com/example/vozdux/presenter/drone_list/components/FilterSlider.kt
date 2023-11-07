package com.example.vozdux.presenter.drone_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp

@Composable
fun FilterSlider(
    name: String,
    value: Float,
    range: ClosedFloatingPointRange<Float>,
    onValueChange: (value: Float) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondary)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Column(
            modifier = Modifier.background(MaterialTheme.colorScheme.secondary)
        ) {
            Text(text = name)
            Slider(
                value = value,
                onValueChange = { onValueChange.invoke(it) },
                valueRange = range
            )
            Text(text = value.toInt().toString())
        }
    }
}
