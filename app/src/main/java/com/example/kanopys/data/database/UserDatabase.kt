package com.example.kanopys.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserDatabase(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val displayName: String,
    val password: String,
    val email: String
)