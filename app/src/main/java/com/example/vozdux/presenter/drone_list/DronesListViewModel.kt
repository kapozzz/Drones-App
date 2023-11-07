package com.example.vozdux.presenter.drone_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vozdux.constants.EMPTY_STRING
import com.example.vozdux.domain.model.drone.Drone
import com.example.vozdux.domain.model.other.DroneOrder
import com.example.vozdux.domain.model.other.OrderType
import com.example.vozdux.domain.usecase.UseCases
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class DronesListViewModel @Inject constructor(
    private val useCases: UseCases,
) : ViewModel() {

    private val _drones: MutableState<List<Drone>> = mutableStateOf(emptyList())
    val drones: State<List<Drone>> = _drones

    private val _screenState: MutableState<DroneListScreenState> =
        mutableStateOf(DroneListScreenState.Loading)
    val screenState: State<DroneListScreenState> = _screenState

    private val _searchQuery: MutableState<String> = mutableStateOf(EMPTY_STRING)
    val searchQuery: State<String> = _searchQuery

    private val _order: MutableState<DroneOrder> =
        mutableStateOf(DroneOrder.MaxVelocity(orderType = OrderType.Ascending))
    val order: State<DroneOrder> =_order

    private val _filterIsVisible: MutableState<Boolean> = mutableStateOf(false)
    val filterIsVisible: State<Boolean> = _filterIsVisible

    private var job: Job? = null

    init {
        refreshDronesList()
        _screenState.value = DroneListScreenState.Screen
    }

    fun onEvent(event: DroneListEvent) {
        when (event) {
            is DroneListEvent.OnSearch -> {
                _searchQuery.value = EMPTY_STRING
                _screenState.value = DroneListScreenState.Search
            }

            is DroneListEvent.QueryChanged -> {
                _searchQuery.value = event.query
                refreshDronesList()
            }

            is DroneListEvent.CloseSearchScreen -> {
                _screenState.value = DroneListScreenState.Screen
            }

            is DroneListEvent.NewOrder -> {
                _order.value = event.order
                refreshDronesList()
            }

            is DroneListEvent.FilterOnClick -> {
                _filterIsVisible.value = !_filterIsVisible.value
            }
        }
    }

    private fun refreshDronesList() {
        job?.cancel()
        job = viewModelScope.launch {
            val isSearching = screenState.value is DroneListScreenState.Search
            useCases.getDrones(
                query = if (isSearching) _searchQuery.value else null,
                droneOrder = if (isSearching) _order.value else null
            ).collect { newList ->
                _drones.value = newList
            }
        }
    }
}