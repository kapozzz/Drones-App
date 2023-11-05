package com.example.vozdux.presenter.new_drone

import com.example.vozdux.domain.model.drone.CompositeDroneElement
import com.example.vozdux.domain.model.drone.Drone
import com.example.vozdux.domain.model.drone.Image
import com.example.vozdux.domain.model.drone.UriImage

sealed class NewDroneScreenEvent {

    data class LongDescriptionElementChanged(val changedElement: CompositeDroneElement) :
        NewDroneScreenEvent()

    data class PropertiesElementChanged(val changedElement: CompositeDroneElement) :
        NewDroneScreenEvent()

    data class DescriptionHeadlineDelete(val descriptionHeadlineToDelete: CompositeDroneElement) :
        NewDroneScreenEvent()

    data class BottomSheetContentChanged(val content: BottomSheetContentState) :
        NewDroneScreenEvent()

    data class FieldChanged(val newDroneState: Drone) : NewDroneScreenEvent()
    data class PropertyDelete(val propertyToDelete: CompositeDroneElement) : NewDroneScreenEvent()
    data class BottomSheetStateChanged(val isVisible: Boolean) : NewDroneScreenEvent()
    data class CurrentExpandedElementChanged(val name: String) : NewDroneScreenEvent()
    data class UrisChanged(val newUris: List<UriImage>) : NewDroneScreenEvent()
    data class CurrentPageChanged(val page: CurrentPropertiesPage?) : NewDroneScreenEvent()
    data class BottomSheetSetId(val id: String) : NewDroneScreenEvent()
    data class ShowImage(val image: UriImage?): NewDroneScreenEvent()
    data class DeleteUriImage(val image: UriImage): NewDroneScreenEvent()
//    data class ScreenStateChanged(val screen: NewDroneScreenState): NewDroneScreenEvent()
    object SaveBottomSheet: NewDroneScreenEvent()
    object SaveDrone : NewDroneScreenEvent()
}
