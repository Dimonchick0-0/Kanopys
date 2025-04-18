package com.example.kanopys.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kanopys.domain.entity.Movie
import com.example.kanopys.domain.entity.Movies
import com.example.kanopys.domain.entity.User
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun registerUser(userDatabase: UserDatabase)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addingToFavorite(movieDatabase: MovieDatabase)

    @Query("DELETE from movieFavorite WHERE id = :id")
    suspend fun deleteFromFavorite(id: Int)

    @Query("SELECT EXISTS(SELECT * FROM users WHERE email = :emailUser)")
    suspend fun checkForEmail(emailUser: String): Boolean

    @Query("SELECT EXISTS(SELECT * FROM movieFavorite WHERE id = :idMovie)")
    suspend fun checkMoviesId(idMovie: Int): Boolean

    @Query("SELECT * FROM users WHERE email = :emailUser AND password = :passwordUser")
    suspend fun authUser(emailUser: String, passwordUser: String): User

    @Query("SELECT * FROM movieFavorite ORDER BY id ASC")
    fun loadAllFavoriteMovies(): Flow<List<Movie>>
}