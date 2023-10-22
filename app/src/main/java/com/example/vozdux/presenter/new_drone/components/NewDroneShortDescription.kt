package com.example.vozdux.presenter.new_drone.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.vozdux.R

@Composable
fun NewDroneShortDescription(
    value: String,
    onValueChange: (newShortDescription: String) -> Unit,
    isError: Boolean,
    modifier: Modifier = Modifier
) {

    Surface(
        modifier = modifier,
        shadowElevation = 2.dp,
        shape = RoundedCornerShape(4.dp)
    ) {

        TextField(
            modifier = modifier,
            minLines = 2,
            value = value,
            onValueChange = onValueChange,
            label = {
                Text(
                    text = stringResource(R.string.short_description),
                    style = MaterialTheme.typography.titleSmall
                )
            },
            isError = isError,
            supportingText = {
                if (isError) {
                    Text(text = stringResource(R.string.short_description_can_t_be_empty))
                }
            },
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                focusedContainerColor = MaterialTheme.colorScheme.secondary
            ),
            shape = RoundedCornerShape(12.dp)
        )
    }
}