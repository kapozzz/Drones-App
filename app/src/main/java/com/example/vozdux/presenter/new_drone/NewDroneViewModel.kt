package com.example.vozdux.presenter.new_drone

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vozdux.constants.emptyDrone
import com.example.vozdux.domain.model.CompositeDroneElement
import com.example.vozdux.domain.model.Cost
import com.example.vozdux.domain.model.Drone
import com.example.vozdux.domain.model.DroneImage
import com.example.vozdux.domain.model.UploadDroneImage
import com.example.vozdux.domain.usecase.UseCases
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class NewDroneViewModel @AssistedInject constructor(
    private val useCases: UseCases,
    @Assisted(value = "droneId") private val droneId: String
) : ViewModel() {

    @AssistedFactory
    interface NewDroneViewModelFactory {
        fun create(@Assisted(value = "droneId") droneId: String): NewDroneViewModel
    }

    private val _currentDrone: MutableState<Drone> = mutableStateOf(emptyDrone)
    val currentDrone: State<Drone> = _currentDrone

    private val _bottomSheetState: MutableState<BottomSheetStateHolder> = mutableStateOf(
        BottomSheetStateHolder()
    )
    val bottomSheetState: State<BottomSheetStateHolder> = _bottomSheetState

    private val _currentExpandedElement: MutableState<String> = mutableStateOf("")
    val currentExpandedElement: State<String> = _currentExpandedElement

    private val _fieldsIsValid: MutableState<FieldIsValidState> = mutableStateOf(
        FieldIsValidState()
    )
    val fieldIsValid: State<FieldIsValidState> = _fieldsIsValid

    private val _uris: MutableState<List<UploadDroneImage>> = mutableStateOf(emptyList())
    val uris: State<List<UploadDroneImage>> = _uris

    private val _currentPage: MutableState<CurrentPage> = mutableStateOf(CurrentPage.Description)
    val currentPage: State<CurrentPage> = _currentPage

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
                nameChanged(event.newName)
            }

            is NewDroneScreenEvent.ShortDescriptionChanged -> {
                shortDescriptionChanged(event.newShortDescription)
            }

            is NewDroneScreenEvent.CreationDateChanged -> {
                creationDateChanged(event.newCreationDate)
            }

            is NewDroneScreenEvent.CostChanged -> {
                costChanged(event.newCost)
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
        }
    }

    private fun bottomSheetSetId(id: String)  {
        _bottomSheetState.value.bottomSheetContentState = _bottomSheetState.value.bottomSheetContentState.copy(
            currentId = id
        )
    }

    private fun nameChanged(newName: String) {
        _currentDrone.value = _currentDrone.value.copy(
            name = newName
        )
    }

    private fun shortDescriptionChanged(newShortDescription: String) {
        _currentDrone.value = _currentDrone.value.copy(
            shortDescription = newShortDescription
        )
    }

    private fun creationDateChanged(newCreationDate: String) {
        _currentDrone.value = _currentDrone.value.copy(
            creationDate = newCreationDate
        )
    }

    private fun costChanged(newCost: Cost) {
        _currentDrone.value = _currentDrone.value.copy(
            cost = newCost
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
        val temp = _currentDrone.value.properties.map {
            if (it.name == changedElement.name) CompositeDroneElement(
                changedElement.name,
                changedElement.value
            )
            else it
        }
        _currentDrone.value = _currentDrone.value.copy(
            properties = temp.toMutableList()
        )
    }

    private fun newProperty() {
        if (_bottomSheetState.value.bottomSheetContentState.currentId != "") {
            _currentDrone.value = _currentDrone.value.copy(
                properties = _currentDrone.value.properties.map {
                    if (it.id == _bottomSheetState.value.bottomSheetContentState.currentId) {
                        CompositeDroneElement(
                            name = _bottomSheetState.value.bottomSheetContentState.name,
                            value = _bottomSheetState.value.bottomSheetContentState.content,
                            id = _bottomSheetState.value.bottomSheetContentState.currentId
                        )
                    } else it
                }.toMutableList()
            )
        }
        else {
            _currentDrone.value.properties.add(
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
        if (_bottomSheetState.value.bottomSheetContentState.currentId != "") {
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
        }
        else {
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
                name = "",
                content = "",
                currentId = ""
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
            val result = useCases.insertDroneUseCase(_currentDrone.value.copy(
                images = _uris.value.map {
                    DroneImage(
                        url = it.id,
                        source = "Source not working!"
                    )
                }
            ))
            if (result.isSuccessful) {
                _uris.value.forEach {
                    useCases.insertDroneImageUseCase(it)
                }
            }
        }
    }

    private fun urisChanged(newUris: List<UploadDroneImage>) {
        _uris.value = newUris
    }

    private fun currentPageChanged(page: CurrentPage) {
        _currentPage.value = page
    }

    private fun bottomSheetContentChanged(contentState: BottomSheetContentState) {
        _bottomSheetState.value = _bottomSheetState.value.copy(
            bottomSheetContentState = contentState
        )
    }
}