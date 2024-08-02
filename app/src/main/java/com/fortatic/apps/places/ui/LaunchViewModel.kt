package com.fortatic.apps.places.ui

import androidx.lifecycle.ViewModel
import com.fortatic.apps.places.data.PlaceDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LaunchViewModel @Inject constructor(
    private val repository: PlaceDataRepository
) : ViewModel() {

    val launchDestination = flow {
        try {
            repository.downloadPlacesFromServer()
            emit(LaunchNavigationAction.MAIN)
        } catch (e: Exception) {
            if (repository.hasLocalData()) {
                emit(LaunchNavigationAction.MAIN)
            } else {
                Timber.e(e,"Remote data download failed")
                emit(LaunchNavigationAction.FAILURE)
            }
        }
    }.flowOn(Dispatchers.IO)
}

enum class LaunchNavigationAction {
    FAILURE, MAIN
}