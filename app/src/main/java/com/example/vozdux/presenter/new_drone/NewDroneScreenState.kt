package com.example.vozdux.presenter.new_drone

import com.example.vozdux.constants.EMPTY_STRING


data class BottomSheetStateHolder(
    var bottomSheetIsVisible: Boolean = false,
    var bottomSheetContentState: BottomSheetContentState = BottomSheetContentState(
        name = EMPTY_STRING,
        content = EMPTY_STRING
    )
)
data class BottomSheetContentState(
    val name: String,
    val content: String,
    val currentId: String = EMPTY_STRING
)

sealed class CurrentPropertiesPage {
    object LongDescription: CurrentPropertiesPage()
    object OtherProperties: CurrentPropertiesPage()
}

sealed class NewDroneScreenState {
    object Loading: NewDroneScreenState()
    object Screen: NewDroneScreenState()
    object PropertiesScreen: NewDroneScreenState()
}




