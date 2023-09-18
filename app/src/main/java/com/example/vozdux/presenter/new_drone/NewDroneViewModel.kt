package com.example.vozdux.presenter.new_drone

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import androidx.lifecycle.ViewModel
import com.example.vozdux.constants.emptyDrone
import com.example.vozdux.domain.Repository
import com.example.vozdux.domain.model.CompositeDroneElement
import com.example.vozdux.domain.model.Drone
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class NewDroneViewModel @AssistedInject constructor(
    private val repository: Repository,
    @Assisted(value = "droneId") private val droneId: String
) : ViewModel() {

    @AssistedFactory
    interface NewDroneViewModelFactory {
        fun create(@Assisted(value = "droneId") droneId: String): NewDroneViewModel
    }

//    private val _state: MutableState<NewDroneScreenState> = mutableStateOf(NewDroneScreenState())
//    val state: State<NewDroneScreenState> = _state

    private val _currentDrone: MutableState<Drone> = mutableStateOf(emptyDrone)
    val currentDrone: State<Drone> = _currentDrone

    private val _bottomSheetState: MutableState<BottomSheetStateHolder> = mutableStateOf(
        BottomSheetStateHolder()
    )
    val bottomSheetState: State<BottomSheetStateHolder> = _bottomSheetState

    init {
        if (droneId != "-1") {
//            _state.value = _state.value.copy(
//                 ебать бля получаем дрон ёпта
//            )
        }
    }

    fun onEvent(event: NewDroneScreenEvent) {
        when (event) {
            is NewDroneScreenEvent.NameChanged -> {
                _currentDrone.value = _currentDrone.value.copy(
                    name = event.newName
                )
            }

            is NewDroneScreenEvent.ShortDescriptionChanged -> {
                _currentDrone.value = _currentDrone.value.copy(
                    shortDescription = event.newShortDescription
                )
            }

            is NewDroneScreenEvent.CreationDateChanged -> {
                _currentDrone.value = _currentDrone.value.copy(
                    creationDate = event.newCreationDate
                )
            }

            is NewDroneScreenEvent.CostChanged -> {
                _currentDrone.value = _currentDrone.value.copy(
                    cost = event.newCost
                )
            }

            is NewDroneScreenEvent.LongDescriptionElementChanged -> {
                val temp = _currentDrone.value.longDescription.map {
                    if (it.name == event.changedElement.name) CompositeDroneElement(event.changedElement.name, event.changedElement.value)
                    else it
                }
                _currentDrone.value = _currentDrone.value.copy(
                    longDescription =  temp.toMutableList()
                )
            }

            is NewDroneScreenEvent.PropertiesElementChanged -> {
                val temp = _currentDrone.value.properties.map {
                    if (it.name == event.changedElement.name) CompositeDroneElement(event.changedElement.name, event.changedElement.value)
                    else it
                }
                _currentDrone.value = _currentDrone.value.copy(
                    properties =  temp.toMutableList()
                )
            }

            is NewDroneScreenEvent.PropertyDelete -> {

            }

            is NewDroneScreenEvent.PropertyNew -> {
                // надо мапа пробовать это хуйня какая-то
                _currentDrone.value.properties.add(
                    CompositeDroneElement(
                        name = _bottomSheetState.value.bottomSheetContentState.propertyField,
                        value = ""
                    )
                )
                _bottomSheetState.value.bottomSheetContentState.propertyField = ""
                _bottomSheetState.value = _bottomSheetState.value.copy(
                    bottomSheetIsVisible = BottomSheetState.BottomSheetIsClosed
                )
            }

            is NewDroneScreenEvent.DescriptionHeadlineDelete -> {

            }

            is NewDroneScreenEvent.DescriptionHeadlineNew -> {
                _currentDrone.value.longDescription.add(
                    CompositeDroneElement(
                        name = _bottomSheetState.value.bottomSheetContentState.descriptionField,
                        value = ""
                    )
                )
                _bottomSheetState.value.bottomSheetContentState.descriptionField = ""
                _bottomSheetState.value = _bottomSheetState.value.copy(
                    bottomSheetIsVisible = BottomSheetState.BottomSheetIsClosed
                )
            }

            is NewDroneScreenEvent.BottomSheetPropertyChanged -> {
                _bottomSheetState.value = _bottomSheetState.value.copy(
                    bottomSheetContentState = _bottomSheetState.value.bottomSheetContentState.copy(
                        propertyField = event.text
                    )
                )
            }

            is NewDroneScreenEvent.BottomSheetDescriptionChanged -> {
                _bottomSheetState.value = _bottomSheetState.value.copy(
                    bottomSheetContentState = _bottomSheetState.value.bottomSheetContentState.copy(
                        descriptionField = event.text
                    )
                )
            }

            is NewDroneScreenEvent.BottomSheetStateChanged -> {
                _bottomSheetState.value = _bottomSheetState.value.copy(
                    bottomSheetIsVisible = event.state
                )
            }
        }
    }

    fun insertDrone() {
        // ?? TODO
//        viewModelScope.launch {
//            repository.insertDrone(_state.value.currentDrone)
//            // TODO
//        }
    }
}