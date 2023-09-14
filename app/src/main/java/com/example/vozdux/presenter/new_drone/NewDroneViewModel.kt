package com.example.vozdux.presenter.new_drone

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.vozdux.domain.Repository
import com.example.vozdux.domain.model.Drone
import dagger.Component.Factory
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class NewDroneViewModel @AssistedInject constructor(
    private val repository: Repository,
    @Assisted(value = "drone") private val currentDrone: Drone?
) : ViewModel() {

    @Factory
    interface NewDroneViewModelFactory {
        fun create()
    }

    private val _state: MutableState<NewDroneScreenState> = mutableStateOf(NewDroneScreenState())
    val state: State<NewDroneScreenState> = _state

    init {
        if (currentDrone != null) {
            _state.value =  _state.value.copy(
                currentDrone = currentDrone
            )
        }
    }

    fun nameChanged(newName: String) {
        val tempCurrentDrone = _state.value.currentDrone.copy(
            name = newName
        )
        _state.value =  _state.value.copy(
            currentDrone = tempCurrentDrone
        )
    }

    fun shortDescriptionChanged(shortDescription: String) {
        val tempCurrentDrone = _state.value.currentDrone.copy(
            shortDescription = shortDescription
        )
        _state.value = _state.value.copy(
            currentDrone = tempCurrentDrone
        )
    }

    fun creationDateChanged(newCreationDate: String) {
        val tempCurrentDrone = _state.value.currentDrone.copy(
            creationDate = newCreationDate
        )
        _state.value =  _state.value.copy(
            currentDrone = tempCurrentDrone
        )
    }

    fun costChanged(newCost: String) {
        val tempCurrentDrone = _state.value.currentDrone.copy(
            cost = newCost
        )
        _state.value =  _state.value.copy(
            currentDrone = tempCurrentDrone
        )
    }

    fun longDescriptionChanged(longDescription: String) {

    }

    fun propertiesChanged() {

    }

    fun insertDrone() {
        // ?? TODO
//        viewModelScope.launch {
//            repository.insertDrone(_state.value.currentDrone)
//            // TODO
//        }
    }
}