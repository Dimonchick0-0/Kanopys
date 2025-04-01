package com.example.kanopys.data.database

import androidx.room.TypeConverter
import com.example.kanopys.domain.entity.Movie
import com.example.kanopys.domain.entity.Movies
import com.example.kanopys.domain.entity.PosterDTO
import com.example.kanopys.domain.entity.RatingDTO

class KanopysConverter {
    @TypeConverter
    fun fromPosterToString(poster: PosterDTO) = poster.url

    @TypeConverter
    fun stringToPoster(url: String) = PosterDTO(url)

    @TypeConverter
    fun fromRatingToFloat(ratingDTO: RatingDTO) = ratingDTO.imdb

    @TypeConverter
    fun floatToRating(imdb: Float) = RatingDTO(imdb)
}