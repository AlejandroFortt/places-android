package com.fortatic.apps.places.data.repository

import com.fortatic.apps.places.data.PlaceDataRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

open class DefaultHomeRepository @Inject constructor(
    private val placeDataRepository: PlaceDataRepository
) {
    open fun getPlaces() = flow {
        emit(placeDataRepository.getOfflinePlaceData().places)
    }
}