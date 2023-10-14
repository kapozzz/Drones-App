package com.example.vozdux.presenter.drone_details

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vozdux.domain.Repository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class DroneDetailsScreenViewModel @AssistedInject constructor(
    private val repository: Repository,
    @Assisted(value = "id") private val droneId: String
) : ViewModel() {

    @AssistedFactory
    interface DroneDetailsScreenViewModelFactory {

        fun create(
            @Assisted(value = "id") droneId: String
        ): DroneDetailsScreenViewModel
    }

    private val _state: MutableState<DroneDetailsState> = mutableStateOf(DroneDetailsState())

    val state: MutableState<DroneDetailsState> = _state

//    init {
//        getDroneById()
//    }

//    private fun getDroneById() {
//        viewModelScope.launch {
//            try {
//                val drone = repository.getDroneById(droneId)
//                _state.value = _state.value.copy(drone = drone)
//            } catch (e: Exception) {
//                Log.e("Error", e.message ?: "empty error message")
//            }
//        }
//    }
}