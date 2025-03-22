package com.example.kanopys.domain.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("id") val id: Int = UNDEFINED_ID,
    @SerialName("display_name") val displayName: String = UNDEFINED_NAME,
    @SerialName("password") val password: String,
    @SerialName("email") val email: String
) {
    companion object {
        private const val UNDEFINED_ID = 0
        private const val UNDEFINED_NAME = ""
    }
}
