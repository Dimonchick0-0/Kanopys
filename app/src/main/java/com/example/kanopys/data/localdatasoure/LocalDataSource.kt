package com.example.kanopys.data.localdatasoure

import com.example.kanopys.domain.entity.Movie
import com.example.kanopys.domain.entity.Movies
import com.example.kanopys.domain.entity.User
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun registerUser(user: User)
    suspend fun checkForEmail(emailUser: String): Boolean
    suspend fun authUser(emailUser: String, passwordUser: String): User
    suspend fun addingToFavorite(movie: Movie)
    suspend fun deleteFromFavorite(id: Int)
    fun loadAllFavoriteMovies(): Flow<List<Movie>>
}