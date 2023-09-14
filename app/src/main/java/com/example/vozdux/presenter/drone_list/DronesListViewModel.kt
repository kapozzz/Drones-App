package com.example.vozdux.presenter.drone_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vozdux.domain.Repository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class DronesListViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _state: MutableState<DronesListState> = mutableStateOf(DronesListState(emptyList()))
    val state: State<DronesListState> = _state

    private var job: Job? = null

    init {
        refreshDronesList()
    }

    private fun refreshDronesList() {
        job?.cancel()
        job = viewModelScope.launch {
            repository.getDrones().collect { drones ->
                _state.value = _state.value.copy(drones = drones)
            }
        }
    }
}