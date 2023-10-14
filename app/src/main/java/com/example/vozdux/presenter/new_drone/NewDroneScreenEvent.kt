package com.example.vozdux.presenter.new_drone

import com.example.vozdux.domain.model.drone.CompositeDroneElement
import com.example.vozdux.domain.model.drone.Drone
import com.example.vozdux.domain.model.drone.Image

sealed class NewDroneScreenEvent {
    data class FieldChanged(val newDroneState: Drone) : NewDroneScreenEvent()

    data class LongDescriptionElementChanged(val changedElement: CompositeDroneElement) :
        NewDroneScreenEvent()

    data class PropertiesElementChanged(val changedElement: CompositeDroneElement) :
        NewDroneScreenEvent()

    data class PropertyDelete(val propertyToDelete: CompositeDroneElement) : NewDroneScreenEvent()
    data class DescriptionHeadlineDelete(val descriptionHeadlineToDelete: CompositeDroneElement) :
        NewDroneScreenEvent()

    data class BottomSheetContentChanged(val content: BottomSheetContentState) :
        NewDroneScreenEvent()

    data class BottomSheetStateChanged(val state: BottomSheetState) : NewDroneScreenEvent()
    data class CurrentExpandedElementChanged(val name: String) : NewDroneScreenEvent()
    data class ErrorInName(val isValid: Boolean) : NewDroneScreenEvent()
    data class ErrorInShortDescription(val isValid: Boolean) : NewDroneScreenEvent()
    data class ErrorInCreationDate(val isValid: Boolean) : NewDroneScreenEvent()
    data class ErrorInCost(val isValid: Boolean) : NewDroneScreenEvent()
    data class UrisChanged(val newUris: List<Image>) : NewDroneScreenEvent()
    data class CurrentPageChanged(val page: CurrentPage) : NewDroneScreenEvent()
    data class BottomSheetSetId(val id: String) : NewDroneScreenEvent()
    object MainPropertiesChanged: NewDroneScreenEvent()
    object PropertyNew : NewDroneScreenEvent()
    object DescriptionHeadlineNew : NewDroneScreenEvent()
    object SaveDrone : NewDroneScreenEvent()
}
