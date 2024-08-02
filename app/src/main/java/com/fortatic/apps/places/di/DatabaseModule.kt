package com.fortatic.apps.places.di

import android.content.Context
import androidx.room.Room
import com.fortatic.apps.places.data.database.PlaceDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val NAME_PLACE_DATABASE = "places"

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): PlaceDatabase {
        val builder = Room.databaseBuilder(
            context,
            PlaceDatabase::class.java,
            NAME_PLACE_DATABASE
        )
            // This is for testing purposes only
            .fallbackToDestructiveMigration()
        return builder.build()
    }
}