package com.fortatic.apps.places.data.network

import com.fortatic.apps.places.data.network.models.PlaceDataNetwork
import retrofit2.http.GET

/**
 * Used to download places data from the API
 */
interface PlaceApiService {

    @GET("places_data")
    suspend fun getPlaceData() : PlaceDataNetwork
}