package com.example.vozdux.presenter.new_drone

import com.example.vozdux.domain.model.CompositeDroneElement

sealed class NewDroneScreenEvent {

    data class NameChanged(val newName: String) : NewDroneScreenEvent()

    data class ShortDescriptionChanged(val newShortDescription: String) : NewDroneScreenEvent()

    data class CreationDateChanged(val newCreationDate: String) : NewDroneScreenEvent()

    data class CostChanged(val newCost: String) : NewDroneScreenEvent()

    data class LongDescriptionElementChanged(val changedElement: CompositeDroneElement) : NewDroneScreenEvent()

    data class PropertiesElementChanged(val changedElement: CompositeDroneElement) : NewDroneScreenEvent()

    data class PropertyDelete(val propertyToDelete: CompositeDroneElement): NewDroneScreenEvent()

    class PropertyNew : NewDroneScreenEvent()

    data class DescriptionHeadlineDelete(val descriptionHeadlineToDelete: CompositeDroneElement) : NewDroneScreenEvent()

    class DescriptionHeadlineNew : NewDroneScreenEvent()

    data class BottomSheetPropertyChanged(val text: String): NewDroneScreenEvent()

    data class BottomSheetDescriptionChanged(val text: String): NewDroneScreenEvent()

    data class BottomSheetStateChanged(val state: BottomSheetState): NewDroneScreenEvent()
}
