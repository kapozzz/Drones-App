package com.example.vozdux.presenter.new_drone.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.vozdux.R

@Composable
fun NewDroneCreationDate(
    value: String,
    onValueChange: (newCreationDate: String) -> Unit,
    isError: Boolean,
    modifier: Modifier = Modifier
) {
    TextField(
        modifier = modifier
            .shadow(2.dp, shape = RoundedCornerShape(4.dp))
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(4.dp)
            ),
        singleLine = true,
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = stringResource(R.string.creation_date),
                style = MaterialTheme.typography.titleSmall
            )
        },
        isError = isError,
        supportingText = {
            if (isError) {
                Text(text = stringResource(R.string.creation_date_can_t_be_empty))
            }
        },
        colors = TextFieldDefaults.colors(
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
            focusedContainerColor = MaterialTheme.colorScheme.secondary
        )
    )
}