package com.example.kanopys.domain.entity

import com.example.kanopys.data.network.PersonsDTO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("year") val year: Int,
    @SerialName("poster") val poster: PosterDTO,
    @SerialName("rating") val rating: RatingDTO,
    @SerialName("description") val description: String,
    @SerialName("persons") val persons: List<PersonsDTO>
)
