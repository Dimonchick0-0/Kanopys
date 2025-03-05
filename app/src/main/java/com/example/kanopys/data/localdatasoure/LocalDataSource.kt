package com.example.kanopys.data.localdatasoure

import com.example.kanopys.domain.entity.User

interface LocalDataSource {
    suspend fun registerUser(user: User)
    suspend fun checkForEmail(emailUser: String): Boolean
}