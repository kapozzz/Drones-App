package com.example.vozdux.presenter.drone_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vozdux.constants.DRONES_LOCAL_DATABASE_NAME
import com.example.vozdux.data.RepositoryImpl
import com.example.vozdux.domain.usecase.UseCases
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class DronesListViewModel @Inject constructor(
    private val useCases: UseCases,
) : ViewModel() {

    private val _state: MutableState<DronesListState> = mutableStateOf(DronesListState(emptyList()))
    val state: State<DronesListState> = _state

    private val _screenState: MutableState<DroneListScreenState> = mutableStateOf(DroneListScreenState.isVisible)
    val screenState: State<DroneListScreenState> = _screenState

    private var job: Job? = null

    init {
        refreshDronesList()
    }

    private fun refreshDronesList() {
        job?.cancel()
        job = viewModelScope.launch {
            useCases.getDrones().collect { newList ->
                _state.value = _state.value.copy(
                    drones = newList
                )
            }
        }
    }
}