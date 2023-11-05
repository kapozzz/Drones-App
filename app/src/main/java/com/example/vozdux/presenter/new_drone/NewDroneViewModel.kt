package com.example.vozdux.presenter.new_drone

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.vozdux.constants.EMPTY_STRING
import com.example.vozdux.constants.emptyDrone
import com.example.vozdux.domain.model.drone.CompositeDroneElement
import com.example.vozdux.domain.model.drone.Drone
import com.example.vozdux.domain.model.drone.UriImage
import com.example.vozdux.domain.usecase.UseCases
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NewDroneViewModel @AssistedInject constructor(
    private val useCases: UseCases,
    @Assisted(value = "navController") private val navController: NavController,
    @Assisted(value = "droneId") private val droneId: String
) : ViewModel() {

    @AssistedFactory
    interface NewDroneViewModelFactory {
        fun create(
            @Assisted(value = "droneId") droneId: String,
            @Assisted(value = "navController") navController: NavController
        ): NewDroneViewModel
    }

    private var _screenState: MutableState<NewDroneScreenState> =
        mutableStateOf(NewDroneScreenState.Screen)
    val screenState: State<NewDroneScreenState> = _screenState

    init {
        if (droneId != "-1") {
            _screenState.value = NewDroneScreenState.Loading
            refreshDrone(droneId)
        }
    }

    private val _currentDrone: MutableState<Drone> = mutableStateOf(emptyDrone)
    val currentDrone: State<Drone> = _currentDrone

    private val _bottomSheetState: MutableState<BottomSheetStateHolder> = mutableStateOf(
        BottomSheetStateHolder()
    )
    val bottomSheetState: State<BottomSheetStateHolder> = _bottomSheetState

    private val _currentExpandedElement: MutableState<String> = mutableStateOf(EMPTY_STRING)
    val currentExpandedElement: State<String> = _currentExpandedElement

    private val _currentPage: MutableState<CurrentPropertiesPage?> =
        mutableStateOf(CurrentPropertiesPage.LongDescription)
    val currentPage: State<CurrentPropertiesPage?> = _currentPage

    private val _currentImageToShow: MutableState<UriImage?> = mutableStateOf(null)
    val currentImageToShow: State<UriImage?> = _currentImageToShow

    private var job: Job? = null

    private fun refreshDrone(droneId: String) {
        job?.cancel()
        job = viewModelScope.launch {
            val result = async {
                delay(500L)
                val drone = useCases.getDroneById(droneId)
                drone?.let { _currentDrone.value = it }
            }
            result.await().also {
                _screenState.value = NewDroneScreenState.Screen
            }
            // TODO ERROR IF NULL
        }
    }

    fun onEvent(event: NewDroneScreenEvent) {
        when (event) {

            is NewDroneScreenEvent.FieldChanged -> {
                fieldChanged(event.newDroneState)
            }

            is NewDroneScreenEvent.LongDescriptionElementChanged -> {
                longDescriptionElementChanged(event.changedElement)
            }

            is NewDroneScreenEvent.PropertiesElementChanged -> {
                propertiesElementChanged(event.changedElement)
            }

            is NewDroneScreenEvent.PropertyDelete -> {
                deletePropertyItem(event.propertyToDelete)
            }

            is NewDroneScreenEvent.DescriptionHeadlineDelete -> {
                deleteLongDescriptionItem(event.descriptionHeadlineToDelete)
            }

            is NewDroneScreenEvent.BottomSheetStateChanged -> {
                bottomSheetStateChanged(event.isVisible)
            }

            is NewDroneScreenEvent.CurrentExpandedElementChanged -> {
                currentExpandedElementChanged(event.name)
            }

            is NewDroneScreenEvent.SaveDrone -> {
                saveDrone()
            }

            is NewDroneScreenEvent.UrisChanged -> {
                urisChanged(event.newUris)
            }

            is NewDroneScreenEvent.CurrentPageChanged -> {
                currentPageChanged(event.page)
            }

            is NewDroneScreenEvent.BottomSheetContentChanged -> {
                bottomSheetContentChanged(event.content)
            }

            is NewDroneScreenEvent.BottomSheetSetId -> {
                bottomSheetSetId(event.id)
            }

            NewDroneScreenEvent.SaveBottomSheet -> {
                if (_currentPage.value is CurrentPropertiesPage.LongDescription) newDescriptionHeadline()
                else newProperty()
            }

            is NewDroneScreenEvent.ShowImage -> {
                changeImageToShow(event.image)
            }

            is NewDroneScreenEvent.DeleteUriImage -> {
                deleteUriImage(event.image)
            }
        }
    }

    private fun deleteLongDescriptionItem(item: CompositeDroneElement) {
        _currentDrone.value = _currentDrone.value.copy(
            longDescription = _currentDrone.value.longDescription.mapNotNull {
                if (it.id == item.id) null else it
            }.toMutableList()
        )
    }

    private fun deletePropertyItem(item: CompositeDroneElement) {
        _currentDrone.value = _currentDrone.value.copy(
            otherProperties = _currentDrone.value.otherProperties.mapNotNull {
                if (it.id == item.id) null else it
            }.toMutableList()
        )
    }

    private fun deleteUriImage(image: UriImage) {
        _currentDrone.value = _currentDrone.value.copy(
            images = _currentDrone.value.images.mapNotNull {
                if (it == image) null else it
            }
        )
    }

    private fun changeImageToShow(image: UriImage?) {
        _currentImageToShow.value = image
    }

    private fun fieldChanged(newDroneState: Drone) {
        _currentDrone.value = newDroneState
    }

    private fun bottomSheetSetId(id: String) {
        _bottomSheetState.value.bottomSheetContentState =
            _bottomSheetState.value.bottomSheetContentState.copy(
                currentId = id
            )
    }

    private fun longDescriptionElementChanged(changedElement: CompositeDroneElement) {
        val temp = _currentDrone.value.longDescription.map {
            if (it.name == changedElement.name) CompositeDroneElement(
                changedElement.name,
                changedElement.value
            )
            else it
        }
        _currentDrone.value = _currentDrone.value.copy(
            longDescription = temp.toMutableList()
        )
    }

    private fun propertiesElementChanged(changedElement: CompositeDroneElement) {
        val temp = _currentDrone.value.otherProperties.map {
            if (it.name == changedElement.name) CompositeDroneElement(
                changedElement.name,
                changedElement.value
            )
            else it
        }
        _currentDrone.value = _currentDrone.value.copy(
            otherProperties = temp.toMutableList()
        )
    }

    private fun newProperty() {
        if (_bottomSheetState.value.bottomSheetContentState.currentId != EMPTY_STRING) {
            _currentDrone.value = _currentDrone.value.copy(
                otherProperties = _currentDrone.value.otherProperties.map {
                    if (it.id == _bottomSheetState.value.bottomSheetContentState.currentId) {
                        CompositeDroneElement(
                            name = _bottomSheetState.value.bottomSheetContentState.name,
                            value = _bottomSheetState.value.bottomSheetContentState.content,
                            id = _bottomSheetState.value.bottomSheetContentState.currentId
                        )
                    } else it
                }.toMutableList()
            )
        } else {
            _currentDrone.value.otherProperties.add(
                CompositeDroneElement(
                    name = _bottomSheetState.value.bottomSheetContentState.name,
                    value = _bottomSheetState.value.bottomSheetContentState.content,
                )
            )
        }
        _bottomSheetState.value = _bottomSheetState.value.copy(bottomSheetIsVisible = false)
        clearBottomSheetContent()
    }

    private fun newDescriptionHeadline() {
        if (_bottomSheetState.value.bottomSheetContentState.currentId != EMPTY_STRING) {
            _currentDrone.value = _currentDrone.value.copy(
                longDescription = _currentDrone.value.longDescription.map {
                    if (it.id == _bottomSheetState.value.bottomSheetContentState.currentId) {
                        CompositeDroneElement(
                            name = _bottomSheetState.value.bottomSheetContentState.name,
                            value = _bottomSheetState.value.bottomSheetContentState.content,
                            id = _bottomSheetState.value.bottomSheetContentState.currentId
                        )
                    } else it
                }.toMutableList()
            )
        } else {
            _currentDrone.value.longDescription.add(
                CompositeDroneElement(
                    name = _bottomSheetState.value.bottomSheetContentState.name,
                    value = _bottomSheetState.value.bottomSheetContentState.content,
                )
            )
        }
        _bottomSheetState.value = _bottomSheetState.value.copy(bottomSheetIsVisible = false)
        clearBottomSheetContent()
    }

    private fun clearBottomSheetContent() {
        _bottomSheetState.value.bottomSheetContentState =
            _bottomSheetState.value.bottomSheetContentState.copy(
                name = EMPTY_STRING,
                content = EMPTY_STRING,
                currentId = EMPTY_STRING
            )
    }

    private fun bottomSheetStateChanged(state: Boolean) {
        _bottomSheetState.value = _bottomSheetState.value.copy(
            bottomSheetIsVisible = state
        )
        if (!state) {
            clearBottomSheetContent()
        }
    }

    private fun currentExpandedElementChanged(name: String) {
        _currentExpandedElement.value = name
    }

    private fun saveDrone() {
        viewModelScope.launch {
            _screenState.value = NewDroneScreenState.Loading
            useCases.insertDrone(_currentDrone.value)
            navController.popBackStack()
        }
    }

    private fun urisChanged(newUris: List<UriImage>) {
        _currentDrone.value = _currentDrone.value.copy(
            images = _currentDrone.value.images.plus(newUris)
        )
    }

    private fun currentPageChanged(page: CurrentPropertiesPage?) {
        _currentPage.value = page
        _currentExpandedElement.value = EMPTY_STRING
        when(page) {
            null -> {
                _screenState.value = NewDroneScreenState.Screen
            }

            else -> {
                _screenState.value = NewDroneScreenState.PropertiesScreen
            }
        }
    }

    private fun bottomSheetContentChanged(contentState: BottomSheetContentState) {
        _bottomSheetState.value = _bottomSheetState.value.copy(
            bottomSheetContentState = contentState
        )
    }
}