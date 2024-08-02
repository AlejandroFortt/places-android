package com.fortatic.apps.places.data

import com.fortatic.apps.places.data.database.PlaceDatabase
import com.fortatic.apps.places.data.database.PlaceEntity
import com.fortatic.apps.places.data.database.toDomainModel
import com.fortatic.apps.places.data.model.PlaceData
import com.fortatic.apps.places.data.network.models.toDatabaseModel
import com.fortatic.apps.places.data.network.models.toDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Single point of access to place data for the presentation layer
 * This ensure one type of source data, remote or local.
 */
@Singleton
class PlaceDataRepository @Inject constructor(
    private val remotePlaceDataSource: PlaceDataSource,
    private val placeDatabase: PlaceDatabase
) {

    // Represents the place cache data
    private var placeDataCache: PlaceData? = null

    // Key to prevent race conditions
    private val mutex = Mutex()

    /**
     * This function only asks to download the remote data
     * @throws Exception when request fails or there is no internet connection
     */
    suspend fun downloadPlacesFromServer() {
        val places = try {
            // Try to get data from server
            remotePlaceDataSource.remoteData()
        } catch (e: Exception) {
            Timber.e(e,"Connection failed, no data from remote")
            null
        }

        if (places == null) {
            val e = Exception("Remote return no data")
            throw e
        }

        Timber.d("places: $places")

        /*
        * This ensure that only one sub process access to this code, to avoid inconsistent data
        * because of concurrency
        * */
        mutex.withLock {
            // Network data success
            placeDataCache = places.toDomainModel()
            updatePlaceDataInLocalStorage(places.toDatabaseModel())
        }
    }

    // Return place data from cache
    suspend fun getOfflinePlaceData() = mutex.withLock {
        withContext(Dispatchers.IO) {
            val cacheData = placeDataCache ?: obtainPlaceDataFromLocalStorage()
            placeDataCache = cacheData
            return@withContext cacheData
        }
    }

    suspend fun hasLocalData() = placeDatabase.placeDao().getAllPlaces().isNotEmpty()

    private suspend fun obtainPlaceDataFromLocalStorage() =
        placeDatabase.placeDao().getAllPlaces().toDomainModel()

    private suspend fun updatePlaceDataInLocalStorage(places: List<PlaceEntity>) =
        placeDatabase.placeDao().insertAll(places)
}