package com.fortatic.apps.places.di

import com.fortatic.apps.places.BuildConfig
import com.fortatic.apps.places.data.network.PlaceApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideRetrofitInstance(
    ): Retrofit {
        val json = Json { ignoreUnknownKeys = true }
        return Retrofit.Builder()
            .addConverterFactory(
                json.asConverterFactory(
                    ("application/json; charset=UTF-8").toMediaType()
                )
            )
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiServices(
        retrofit: Retrofit
    ): PlaceApiService = retrofit.create(PlaceApiService::class.java)
}