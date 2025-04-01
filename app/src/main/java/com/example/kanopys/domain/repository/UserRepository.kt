package com.example.kanopys.domain.repository

import com.example.kanopys.domain.entity.Movie
import com.example.kanopys.domain.entity.User

interface UserRepository {
    suspend fun registerAProfile(user: User)

    suspend fun authenticationUser(emailUser: String, passwordUser: String): User

    suspend fun addingToFavorites(item: Movie)

    suspend fun deleteFromFavorite(id: Int)
}