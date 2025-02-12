package com.example.kanopys.data.localdatastore

import com.example.kanopys.domain.entity.User

interface LocalDataSource {
    suspend fun registerUser(user: User)
}