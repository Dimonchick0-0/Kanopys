package com.example.kanopys.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserDatabase(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val password: String
)