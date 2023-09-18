package com.example.vozdux.presenter.new_drone

data class BottomSheetStateHolder(
    var bottomSheetIsVisible: BottomSheetState = BottomSheetState.BottomSheetIsClosed,
    val bottomSheetContentState: BottomSheetContentState = BottomSheetContentState(
        propertyField = "",
        descriptionField = ""
    )
)

data class BottomSheetContentState(
    var propertyField: String,
    var descriptionField: String
)

sealed class BottomSheetState {
    object NewDescriptionHeadline : BottomSheetState()
    object NewProperty : BottomSheetState()
    object BottomSheetIsClosed : BottomSheetState()
}

