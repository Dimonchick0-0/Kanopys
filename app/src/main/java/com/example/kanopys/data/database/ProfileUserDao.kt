package com.example.kanopys.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kanopys.domain.entity.User

@Dao
interface ProfileUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun registerUser(userDatabase: UserDatabase)

    @Query("SELECT EXISTS(SELECT * FROM users WHERE email = :emailUser)")
    suspend fun checkForEmail(emailUser: String): Boolean

    @Query("SELECT * FROM users WHERE email = :emailUser AND password = :passwordUser")
    suspend fun authUser(emailUser: String, passwordUser: String): User
}