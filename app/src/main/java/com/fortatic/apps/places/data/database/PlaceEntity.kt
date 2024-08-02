package com.fortatic.apps.places.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fortatic.apps.places.data.model.Place
import com.fortatic.apps.places.data.model.PlaceData

@Entity(tableName = "places")
data class PlaceEntity(
    @ColumnInfo(name = "place_id") @PrimaryKey val placeId: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "coordinates") val coordinates: String,
    @ColumnInfo(name = "rate") val rate: String,
    @ColumnInfo(name = "country") val country: String,
    @ColumnInfo(name = "city") val city: String,
    @ColumnInfo(name = "temperature") val temperature: String,
    @ColumnInfo(name = "weather") val weather: String,
    @ColumnInfo(name = "activities") val activities: String
)

fun List<PlaceEntity>.toDomainModel(): PlaceData {
    return PlaceData(map { it.toPlace() })
}

fun PlaceEntity.toPlace(): Place {
    return Place(
        id = placeId,
        name = name,
        description = description,
        imageUrl = imageUrl,
        coordinates = coordinates,
        rate = rate,
        country = country,
        city = city,
        temperature = temperature,
        weather = weather,
        activities = activities.split(",")
    )
}