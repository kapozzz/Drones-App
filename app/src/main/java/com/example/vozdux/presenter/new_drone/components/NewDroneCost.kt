package com.example.vozdux.presenter.new_drone.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.vozdux.domain.model.Cost
import com.example.vozdux.domain.model.Currency

@Composable
fun NewDroneCost(
    value: Cost,
    onValueChange: (newValue: String) -> Unit,
    isError: Boolean,
    currentCurrencyChanged: (newCost: Cost) -> Unit,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        TextField(
            modifier = modifier,
            singleLine = true,
            value = value.value,
            onValueChange = onValueChange,
            label = {
                Text(
                    text = "Cost",
                    style = MaterialTheme.typography.titleSmall
                )
            },
            isError = isError,
            supportingText = {
                if (isError) {
                    Text(text = "Cost can't be empty")
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            suffix = {
                Button(
                    onClick = {
                        val newCost = value.copy(
                            currency = if (value.currency is Currency.RUB) Currency.USD else Currency.RUB
                        )
                        currentCurrencyChanged(newCost)
                    }
                ) {
                    Text(
                        text = value.currency.value
                    )
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