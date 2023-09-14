package com.example.vozdux.presenter.new_drone.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun CompositeElement(
    onElementChanged: (List<Map<String, String>>) -> Unit,
    modifier: Modifier = Modifier,
    elements: List<Map<String, String>> = emptyList()
) {

    val localElementsState = remember {
        mutableStateOf(elements)
    }

    LazyColumn {

        items(localElementsState.value) { element ->

            var key: String
            var value: String

            element.forEach { (_key, _value) ->

            }

            TextField(value = element, onValueChange = {

            })
        }
    }
}