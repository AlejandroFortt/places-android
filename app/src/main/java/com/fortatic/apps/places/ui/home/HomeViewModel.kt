package com.fortatic.apps.places.ui.home

import androidx.lifecycle.ViewModel
import com.fortatic.apps.places.domain.home.LoadPlacesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    loadPlacesUseCase: LoadPlacesUseCase
) : ViewModel() {

    val places = loadPlacesUseCase(Unit)
}