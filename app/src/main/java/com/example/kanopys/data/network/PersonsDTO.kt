package com.example.kanopys.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonsDTO(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("photo") val photo: String,
    @SerialName("enName") val enName: String,
    @SerialName("description") val description: String,
    @SerialName("profession") val profession: String
)
