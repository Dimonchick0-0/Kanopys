package com.example.kanopys.domain.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("id") val id: Int = UNDEFINED_ID,
    @SerialName("name") val name: String,
    @SerialName("password") val password: String,
    @SerialName("token") val token: String = UNDEFINED_TOKEN
) {
    companion object {
        private const val UNDEFINED_ID = 0
        private const val UNDEFINED_TOKEN = ""
    }
}
