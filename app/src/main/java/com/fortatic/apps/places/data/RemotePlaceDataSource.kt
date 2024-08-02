package com.fortatic.apps.places.data

import com.fortatic.apps.places.data.network.PlaceApiService
import com.fortatic.apps.places.data.network.models.PlaceDataNetwork
import com.fortatic.apps.places.util.NetworkUtils
import timber.log.Timber
import javax.inject.Inject

interface PlaceDataSource {
    suspend fun remoteData(): PlaceDataNetwork?
    fun localData(): PlaceDataNetwork?
}

/**
 * Download places data parsed by Retrofit
 */
open class RemotePlaceDataSource @Inject constructor(
    private val placeApiService: PlaceApiService,
    private val networkUtils: NetworkUtils
) : PlaceDataSource {

    private suspend fun getRemotePlaceData(): PlaceDataNetwork {
        return placeApiService.getPlaceData()
    }

    /**
     * Returns the remote data or null if there is no internet connection
     */
    override suspend fun remoteData(): PlaceDataNetwork? {
        return if (!networkUtils.hasNetworkConnection()) {
            Timber.d("No internet connection")
            null
        } else getRemotePlaceData()
    }

    override fun localData(): PlaceDataNetwork? {
        return null
    }

}