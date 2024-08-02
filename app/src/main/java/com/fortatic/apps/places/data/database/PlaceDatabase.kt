package com.fortatic.apps.places.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * The [Room] database for this app used as a local storage
 */
@Database(
    entities = [PlaceEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PlaceDatabase: RoomDatabase() {
    abstract fun placeDao(): PlaceDao
}