package com.example.kanopys.data.repository

import com.example.kanopys.data.localdatasoure.LocalDataSourceImpl
import com.example.kanopys.domain.entity.Movie
import com.example.kanopys.domain.entity.Movies
import com.example.kanopys.domain.entity.User
import com.example.kanopys.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val localDataSourceImpl: LocalDataSourceImpl
) : UserRepository {

    suspend fun checkForEmail(emailUser: String): Boolean {
        return localDataSourceImpl.checkForEmail(emailUser)
    }

    suspend fun checkMovieFavorite(id: Int): Boolean {
        return localDataSourceImpl.checkMoviesId(id)
    }

    fun loadAllFavoriteMovies(): Flow<List<Movie>> {
        return localDataSourceImpl.loadAllFavoriteMovies()
    }

    override suspend fun registerAProfile(user: User) {
        localDataSourceImpl.registerUser(user)
    }

    override suspend fun authenticationUser(emailUser: String, passwordUser: String): User {
        val user = localDataSourceImpl.authUser(emailUser, passwordUser)
        return user
    }

    override suspend fun addingToFavorites(item: Movie) {
        localDataSourceImpl.addingToFavorite(item)
    }

    override suspend fun deleteFromFavorite(id: Int) {
        localDataSourceImpl.deleteFromFavorite(id)
    }
}