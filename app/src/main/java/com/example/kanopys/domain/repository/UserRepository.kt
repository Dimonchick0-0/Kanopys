package com.example.kanopys.domain.repository

import com.example.kanopys.domain.entity.User

interface UserRepository {
    suspend fun registerAProfile(user: User)

    suspend fun logInToYourProfile(user: User)

    suspend fun logOutOfYourProfile(user: User)
}