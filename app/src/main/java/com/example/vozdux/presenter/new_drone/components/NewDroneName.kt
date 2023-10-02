package com.example.vozdux.presenter.new_drone.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun NewDroneName(
    value: String,
    onValueChange: (newName: String) -> Unit,
    isError: Boolean,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            label = {
                Text(
                    text = "Name of drone",
                    style = MaterialTheme.typography.titleSmall
                )
            },
            singleLine = true,
            isError = isError,
            supportingText = {
                if (isError) {
                    Text(text = "Drone name can't be empty")
                }
            },
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(12.dp)
        )
    }
}