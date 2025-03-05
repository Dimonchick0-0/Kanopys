package com.example.kanopys.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProfileUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun registerUser(userDatabase: UserDatabase)

    @Query("SELECT EXISTS(SELECT * FROM users WHERE email = :emailUser)")
    suspend fun checkForEmail(emailUser: String): Boolean
}