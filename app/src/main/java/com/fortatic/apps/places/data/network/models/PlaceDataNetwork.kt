package com.fortatic.apps.places.data.network.models

import com.fortatic.apps.places.data.database.PlaceEntity
import com.fortatic.apps.places.data.model.Place
import com.fortatic.apps.places.data.model.PlaceData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaceDataNetwork(
    val places: List<PlaceNetwork>
)

@Serializable
data class PlaceNetwork(
    val id: String,
    val name: String,
    val description: String,
    @SerialName("image_url") val imageUrl: String,
    val coordinates: String,
    val rate: String,
    val country: String,
    val city: String,
    val temperature: String,
    val weather: String,
    val activities: List<String>
)

fun PlaceDataNetwork.toDatabaseModel(): List<PlaceEntity> {
    return places.map {
        PlaceEntity(
            placeId = it.id,
            name = it.name,
            description = it.description,
            imageUrl = it.imageUrl,
            coordinates = it.coordinates,
            rate = it.rate,
            country = it.country,
            city = it.city,
            temperature = it.temperature,
            weather = it.weather,
            activities = it.activities.joinToString()
        )
    }
}

fun PlaceDataNetwork.toDomainModel(): PlaceData {
    return PlaceData(places.map { it.toPlace() })
}

fun PlaceNetwork.toPlace(): Place {
    return Place(
        id = id,
        name = name,
        description = description,
        imageUrl = imageUrl,
        coordinates = coordinates,
        rate = rate,
        country = country,
        city = city,
        temperature = temperature,
        weather = weather,
        activities = activities
    )
}