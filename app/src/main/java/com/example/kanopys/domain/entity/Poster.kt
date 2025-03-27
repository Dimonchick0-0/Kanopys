package com.example.kanopys.domain.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Poster(
    @SerialName("url") val url: String
)