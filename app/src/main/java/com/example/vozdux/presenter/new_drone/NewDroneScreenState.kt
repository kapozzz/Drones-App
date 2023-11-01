package com.example.vozdux.presenter.new_drone


data class BottomSheetStateHolder(
    var bottomSheetIsVisible: Boolean = false,
    var bottomSheetContentState: BottomSheetContentState = BottomSheetContentState(
        name = "",
        content = ""
    )
)
data class BottomSheetContentState(
    val name: String,
    val content: String,
    val currentId: String = ""
)
sealed class CurrentPage {
    object Description: CurrentPage()
    object Properties: CurrentPage()
    object  MainProperties: CurrentPage()
}

sealed class NewDroneScreenState {
    object Loading: NewDroneScreenState()
    object Screen: NewDroneScreenState()
}




