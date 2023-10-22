package com.example.vozdux.presenter.new_drone.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.vozdux.R
import com.example.vozdux.domain.model.drone.Cost
import com.example.vozdux.domain.model.drone.RUB_CODE
import com.example.vozdux.domain.model.drone.USD_CODE

@Composable
fun NewDroneCost(
    value: Cost,
    onValueChange: (newValue: String) -> Unit,
    isError: Boolean,
    currentCurrencyChanged: (newCost: Cost) -> Unit,
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
        value = value.value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = stringResource(R.string.cost),
                style = MaterialTheme.typography.titleSmall
            )
        },
        isError = isError,
        supportingText = {
            if (isError) {
                Text(text = stringResource(R.string.cost_can_t_be_empty))
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        suffix = {
            Button(
                onClick = {
                    val newCost = value.copy(
                        currency = if (value.currency == RUB_CODE) USD_CODE else RUB_CODE
                    )
                    currentCurrencyChanged(newCost)
                }
            ) {
                Text(
                    text = value.currency,
                    style = MaterialTheme.typography.bodySmall
                )
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