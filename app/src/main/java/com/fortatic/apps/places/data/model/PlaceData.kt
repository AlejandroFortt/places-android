package com.fortatic.apps.places.data.model

data class PlaceData(
    val places: List<Place>
)

data class Place(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val coordinates: String,
    val rate: String,
    val country: String,
    val city: String,
    val temperature: String,
    val weather: String,
    val activities: List<String>,
    val price: String
)

fun List<Place>.toHomePlace(): List<HomePlace> {
    return map {
        HomePlace(
            id = it.id,
            name = it.name,
            imageUrl = it.imageUrl,
            rate = it.rate,
            country = it.country,
            city = it.city
        )
    }
}