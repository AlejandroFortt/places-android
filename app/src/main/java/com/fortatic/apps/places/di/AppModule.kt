package com.fortatic.apps.places.di

import com.fortatic.apps.places.data.PlaceDataRepository
import com.fortatic.apps.places.data.PlaceDataSource
import com.fortatic.apps.places.data.RemotePlaceDataSource
import com.fortatic.apps.places.data.database.PlaceDatabase
import com.fortatic.apps.places.data.network.PlaceApiService
import com.fortatic.apps.places.util.NetworkUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Defines all the classes that need to be provided in the scope of the app
 */
@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun provideRemoteDataSource(
        placeApiService: PlaceApiService,
        networkUtils: NetworkUtils
    ): PlaceDataSource {
        return RemotePlaceDataSource(placeApiService, networkUtils)
    }

    @Singleton
    @Provides
    fun providePlaceDataRepository(
        remotePlaceDataSource: PlaceDataSource,
        placeDatabase: PlaceDatabase
    ): PlaceDataRepository {
        return PlaceDataRepository(remotePlaceDataSource, placeDatabase)
    }
}