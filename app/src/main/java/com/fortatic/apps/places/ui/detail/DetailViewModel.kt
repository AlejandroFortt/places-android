package com.fortatic.apps.places.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fortatic.apps.places.data.model.Place
import com.fortatic.apps.places.domain.detail.FindSpecificPlaceUseCase
import com.fortatic.apps.places.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val saveState: SavedStateHandle,
    private val findSpecificPlaceUseCase: FindSpecificPlaceUseCase
) : ViewModel() {

    private val _placeData = MutableStateFlow<Result<Place>>(Result.Loading)
    val placeData: StateFlow<Result<Place>> = _placeData

    private val _mapUiState = MutableStateFlow<MapUiState>(MapUiState.Empty)
    val mapUiState: StateFlow<MapUiState> = _mapUiState

    var placeName: String = ""

    init {
        viewModelScope.launch {
            _placeData.update {
                // Obtain the args passed by navigation
                saveState.get<String>("place_id").let {
                    // Update with the result
                    if (!it.isNullOrEmpty()) {
                        findSpecificPlaceUseCase(it).first()
                    } else {
                        Result.Error(Exception("Error trying to get place data"))
                    }
                }
            }
        }
    }

    fun setMapData(data: Place) {
        placeName = data.name
        val (latitude, longitude) = data.coordinates.split(",")
        _mapUiState.value = MapUiState.UpdateMarker(latitude, longitude, data.name)
    }
}

sealed class MapUiState {
    data object Empty : MapUiState()
    data class UpdateMarker(val latitude: String, val longitude: String, val name: String) : MapUiState()
}