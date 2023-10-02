package com.example.vozdux.presenter.new_drone

import com.example.vozdux.domain.model.UploadDroneImage

data class BottomSheetStateHolder(
    var bottomSheetIsVisible: BottomSheetState = BottomSheetState.BottomSheetIsClosed,
    var bottomSheetContentState: BottomSheetContentState = BottomSheetContentState(
        name = "",
        content = ""
    )
)

data class UrisState(
    val urisState: List<UploadDroneImage>,
    val isVisible: Boolean = false
)

data class BottomSheetContentState(
    val name: String,
    val content: String,
    val currentId: String = ""
)
data class FieldIsValidState(
    val nameIsEmpty: Boolean = false,
    val shortDescriptionIsEmpty: Boolean = false,
    val creationDateIsEmpty: Boolean = false,
    val costIsEmpty: Boolean = false
)
sealed class BottomSheetState {
    object NewDescriptionHeadline : BottomSheetState()
    object NewProperty : BottomSheetState()
    object BottomSheetIsClosed : BottomSheetState()
}
sealed class CurrentPage {
    object Description: CurrentPage()
    object Properties: CurrentPage()
}





