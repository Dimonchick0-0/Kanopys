package com.example.kanopys.data.localdatasoure

import androidx.lifecycle.LiveData
import com.example.kanopys.domain.entity.User

interface LocalDataSource {
    suspend fun registerUser(user: User)
    suspend fun checkForEmail(emailUser: String): Boolean
    suspend fun authUser(emailUser: String, passwordUser: String): User
}