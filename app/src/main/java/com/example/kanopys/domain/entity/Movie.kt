package com.example.kanopys.domain.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("poster") val poster: Poster
)
