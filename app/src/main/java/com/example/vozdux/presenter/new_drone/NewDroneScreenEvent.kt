package com.example.vozdux.presenter.new_drone

import com.example.vozdux.domain.model.CompositeDroneElement
import com.example.vozdux.domain.model.Cost
import com.example.vozdux.domain.model.UploadDroneImage

sealed class NewDroneScreenEvent {

    data class NameChanged(val newName: String) : NewDroneScreenEvent()

    data class ShortDescriptionChanged(val newShortDescription: String) : NewDroneScreenEvent()

    data class CreationDateChanged(val newCreationDate: String) : NewDroneScreenEvent()

    data class CostChanged(val newCost: Cost) : NewDroneScreenEvent()

    data class LongDescriptionElementChanged(val changedElement: CompositeDroneElement) :
        NewDroneScreenEvent()

    data class PropertiesElementChanged(val changedElement: CompositeDroneElement) :
        NewDroneScreenEvent()

    data class PropertyDelete(val propertyToDelete: CompositeDroneElement) : NewDroneScreenEvent()
    data class DescriptionHeadlineDelete(val descriptionHeadlineToDelete: CompositeDroneElement) :
        NewDroneScreenEvent()
    data class BottomSheetContentChanged(val content: BottomSheetContentState) : NewDroneScreenEvent()
    data class BottomSheetStateChanged(val state: BottomSheetState) : NewDroneScreenEvent()
    data class CurrentExpandedElementChanged(val name: String) : NewDroneScreenEvent()
    data class ErrorInName(val isValid: Boolean) : NewDroneScreenEvent()
    data class ErrorInShortDescription(val isValid: Boolean) : NewDroneScreenEvent()
    data class ErrorInCreationDate(val isValid: Boolean) : NewDroneScreenEvent()
    data class ErrorInCost(val isValid: Boolean) : NewDroneScreenEvent()
    data class UrisChanged(val newUris: List<UploadDroneImage>): NewDroneScreenEvent()
    data class CurrentPageChanged(val page: CurrentPage): NewDroneScreenEvent()
    data class BottomSheetSetId(val id: String): NewDroneScreenEvent()
    object PropertyNew : NewDroneScreenEvent()
    object DescriptionHeadlineNew : NewDroneScreenEvent()
    object SaveDrone: NewDroneScreenEvent()
}
