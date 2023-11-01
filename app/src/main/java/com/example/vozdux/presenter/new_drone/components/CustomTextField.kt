package com.example.vozdux.presenter.new_drone.components

import android.text.BoringLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.vozdux.R
import com.example.vozdux.constants.EMPTY_STRING

@Composable
fun CustomTextField(
    value: String,
    onValueChanged: (newValue: String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    singleLine: Boolean = false,
    readOnly: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    val isError = remember {
        mutableStateOf(false)
    }

    TextField(
        modifier = modifier
            .shadow(2.dp, shape = RoundedCornerShape(4.dp))
            .background(
                color = MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(4.dp)
            ),
        value = value,
        onValueChange = { newValue ->
            isError.value = newValue.isEmpty()
            onValueChanged.invoke(newValue)
        },
        label = {
            if (!label.isNullOrEmpty()) Text(
                text = label,
                style = MaterialTheme.typography.titleSmall
            )
        },
        isError = isError.value,
        supportingText = {
            if (isError.value) {
                Text(
                    text = label + " " + stringResource(R.string.can_t_be_empty)
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
        ),
        readOnly = readOnly,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        keyboardOptions = keyboardOptions
    )
}