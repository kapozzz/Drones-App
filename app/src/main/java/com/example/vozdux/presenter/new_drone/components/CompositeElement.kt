package com.example.vozdux.presenter.new_drone.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.vozdux.domain.model.CompositeDroneElement

@Composable
fun CompositeElement(
    element: CompositeDroneElement,
    onElementChanged: (CompositeDroneElement) -> Unit,
    changeExpandedElement: (name: String) -> Unit,
    modifier: Modifier = Modifier,
    currentExpandedElement: Boolean = false
) {

    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        
        Column {
            Button(onClick = {
                changeExpandedElement(element.name)
            }) {
                Text(
                    text = element.name + ":",
                )
            }


            if (currentExpandedElement) TextField(
                modifier = Modifier.fillMaxWidth(),
                value = element.value, onValueChange = { newValue ->
                    val updatedElement = element.copy(
                        value = newValue
                    )
                    onElementChanged.invoke(updatedElement)
                })
        }
    }
}