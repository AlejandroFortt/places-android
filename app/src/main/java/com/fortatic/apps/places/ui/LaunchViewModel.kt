package com.fortatic.apps.places.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class LaunchViewModel @Inject constructor() : ViewModel() {

    val launchDestination = flow {
        // Simulate a future network request
        delay(2000)
        emit(LaunchNavigationAction.MAIN)
    }
}

enum class LaunchNavigationAction {
    FAILURE, MAIN
}