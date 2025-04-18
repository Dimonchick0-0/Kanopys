package com.example.kanopys.data.localdatasoure

import com.example.kanopys.data.database.ProfileUserDao
import com.example.kanopys.data.mapping.MappingUser
import com.example.kanopys.domain.entity.Movie
import com.example.kanopys.domain.entity.Movies
import com.example.kanopys.domain.entity.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val mapper: MappingUser,
    private val profileUserDao: ProfileUserDao
): LocalDataSource {
    override suspend fun registerUser(user: User) {
        profileUserDao.registerUser(mapper.mapEntityUserToDatabaseUser(user))
    }

    override suspend fun checkForEmail(emailUser: String): Boolean {
        return profileUserDao.checkForEmail(emailUser)
    }

    override suspend fun authUser(emailUser: String, passwordUser: String): User {
        return profileUserDao.authUser(emailUser, passwordUser)
    }

    override suspend fun addingToFavorite(movie: Movie) {
        profileUserDao.addingToFavorite(mapper.mapEntityMovieToDBMovie(movie))
    }

    override suspend fun deleteFromFavorite(id: Int) {
        profileUserDao.deleteFromFavorite(id)
    }

    override fun loadAllFavoriteMovies(): Flow<List<Movie>> {
        return profileUserDao.loadAllFavoriteMovies()
    }

    override suspend fun checkMoviesId(id: Int): Boolean {
        return profileUserDao.checkMoviesId(id)
    }
}