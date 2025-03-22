package com.example.kanopys.domain.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movies( @SerialName("docs") val docs: List<Movie>)