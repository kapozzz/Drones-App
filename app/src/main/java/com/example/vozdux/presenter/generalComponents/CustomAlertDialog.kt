package com.example.vozdux.presenter.generalComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.vozdux.R

@Composable
fun CustomAlertDialog(
    label: String,
    positive: () -> Unit,
    negative: () -> Unit
) {

    AlertDialog(
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = label,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Left
            )
        },
        buttons = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = { negative.invoke() }) {
                    Text(
                        text = stringResource(R.string.dismiss),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
                TextButton(onClick = {
                    positive.invoke()
                }) {
                    Text(
                        text = stringResource(R.string.confirm),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        },
        onDismissRequest = { negative.invoke() }
    )
}