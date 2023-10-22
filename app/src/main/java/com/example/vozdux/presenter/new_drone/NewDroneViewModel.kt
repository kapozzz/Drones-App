package com.example.vozdux.presenter.new_drone

import android.util.Log
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
import com.example.vozdux.domain.model.drone.Image
import com.example.vozdux.domain.model.drone.ImageSourceId
import com.example.vozdux.domain.model.drone.toProperties
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

    private var _screenState: MutableState<NewDroneScreenState> = mutableStateOf(NewDroneScreenState.Screen)
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

    private val _fieldsIsValid: MutableState<FieldIsValidState> = mutableStateOf(
        FieldIsValidState()
    )
    val fieldIsValid: State<FieldIsValidState> = _fieldsIsValid

    private val _uris: MutableState<List<Image>> = mutableStateOf(emptyList())
    val uris: State<List<Image>> = _uris

    private val _currentPage: MutableState<CurrentPage> = mutableStateOf(CurrentPage.Description)
    val currentPage: State<CurrentPage> = _currentPage

    private var job: Job? = null

    private fun refreshDrone(droneId: String) {
        job?.cancel()
        job = viewModelScope.launch {
            val result = async {
                delay(2000L)
                val droneWithImages = useCases.getDroneById(droneId)
                droneWithImages?.let {
                    _currentDrone.value = it.drone
                    _uris.value = it.images
                }
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
                TODO()
            }

            is NewDroneScreenEvent.DescriptionHeadlineDelete -> {
                TODO()
            }

            is NewDroneScreenEvent.PropertyNew -> {
                newProperty()
            }

            is NewDroneScreenEvent.DescriptionHeadlineNew -> {
                newDescriptionHeadline()
            }

            is NewDroneScreenEvent.BottomSheetStateChanged -> {
                bottomSheetStateChanged(event.state)
            }

            is NewDroneScreenEvent.CurrentExpandedElementChanged -> {
                currentExpandedElementChanged(event.name)
            }

            is NewDroneScreenEvent.ErrorInName -> {
                errorInName(event.isValid)
            }

            is NewDroneScreenEvent.ErrorInShortDescription -> {
                errorInShortDescription(event.isValid)
            }

            is NewDroneScreenEvent.ErrorInCreationDate -> {
                errorInCreationDate(event.isValid)
            }

            is NewDroneScreenEvent.ErrorInCost -> {
                errorInCost(event.isValid)
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

            is NewDroneScreenEvent.MainPropertiesChanged -> {
                mainPropertiesChanged()
            }
        }
    }

    private fun mainPropertiesChanged() {
        _currentDrone.value = _currentDrone.value.copy(
            mainProperties = _currentDrone.value.mainProperties.toList().map {

                if (it.name == _bottomSheetState.value.bottomSheetContentState.name) {
                    it.copy(
                        value = _bottomSheetState.value.bottomSheetContentState.content.toInt()
                    )
                } else it
            }.toProperties()
        )
        _bottomSheetState.value = _bottomSheetState.value.copy(
            bottomSheetIsVisible = BottomSheetState.BottomSheetIsClosed
        )
        clearBottomSheetContent()
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
        _bottomSheetState.value = _bottomSheetState.value.copy(
            bottomSheetIsVisible = BottomSheetState.BottomSheetIsClosed
        )
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
        _bottomSheetState.value = _bottomSheetState.value.copy(
            bottomSheetIsVisible = BottomSheetState.BottomSheetIsClosed
        )
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


    private fun bottomSheetStateChanged(state: BottomSheetState) {
        _bottomSheetState.value = _bottomSheetState.value.copy(
            bottomSheetIsVisible = state
        )
        if (state is BottomSheetState.BottomSheetIsClosed) {
            clearBottomSheetContent()
        }
    }

    private fun currentExpandedElementChanged(name: String) {
        _currentExpandedElement.value = name
    }

    private fun errorInName(isValid: Boolean) {
        _fieldsIsValid.value = _fieldsIsValid.value.copy(
            nameIsEmpty = isValid
        )
    }

    private fun errorInShortDescription(isValid: Boolean) {
        _fieldsIsValid.value = _fieldsIsValid.value.copy(
            shortDescriptionIsEmpty = isValid
        )
    }

    private fun errorInCreationDate(isValid: Boolean) {
        _fieldsIsValid.value = _fieldsIsValid.value.copy(
            creationDateIsEmpty = isValid
        )
    }

    private fun errorInCost(isValid: Boolean) {
        _fieldsIsValid.value = _fieldsIsValid.value.copy(
            costIsEmpty = isValid
        )
    }

    private fun saveDrone() {
        viewModelScope.launch {
            val isSuccessful = useCases.insertDrone(_currentDrone.value.copy(
                imageIDs = _uris.value.map {
                    ImageSourceId(
                        id = it.id,
                        source = "source not working!"
                    )
                }
            ))
            if (isSuccessful) {
                _uris.value.forEach { image ->
                    useCases.insertImage(image)
                }
            }
            navController.popBackStack()
        }
    }

    private fun urisChanged(newUris: List<Image>) {
        _uris.value = newUris
    }

    private fun currentPageChanged(page: CurrentPage) {
        _currentPage.value = page
        _currentExpandedElement.value = EMPTY_STRING
    }

    private fun bottomSheetContentChanged(contentState: BottomSheetContentState) {
        _bottomSheetState.value = _bottomSheetState.value.copy(
            bottomSheetContentState = contentState
        )
    }
}