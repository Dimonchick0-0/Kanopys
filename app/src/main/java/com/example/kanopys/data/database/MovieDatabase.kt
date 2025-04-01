package com.example.kanopys.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.kanopys.domain.entity.PosterDTO
import com.example.kanopys.domain.entity.RatingDTO

@Entity(tableName = "movieFavorite")
data class MovieDatabase(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val year: Int,
    val poster: PosterDTO,
    val rating: RatingDTO,
    val description: String
)
