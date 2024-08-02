package com.fortatic.apps.places.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * The Data Access Object for the [PlaceEntity] class
 */
@Dao
interface PlaceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(placeEntities: List<PlaceEntity>)

    @Query("SELECT * FROM places")
    suspend fun getAllPlaces(): List<PlaceEntity>
}