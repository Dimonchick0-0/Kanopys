package com.example.kanopys.data.database

import androidx.room.TypeConverter
import com.example.kanopys.data.network.PersonsDTO
import com.example.kanopys.domain.entity.Movie
import com.example.kanopys.domain.entity.Movies
import com.example.kanopys.domain.entity.PosterDTO
import com.example.kanopys.domain.entity.RatingDTO
import com.google.gson.Gson

class KanopysConverter {
    @TypeConverter
    fun fromPosterToString(poster: PosterDTO) = poster.url

    @TypeConverter
    fun stringToPoster(url: String) = PosterDTO(url)

    @TypeConverter
    fun fromRatingToFloat(ratingDTO: RatingDTO) = ratingDTO.imdb

    @TypeConverter
    fun floatToRating(imdb: Float) = RatingDTO(imdb)

    @TypeConverter
    fun listToJson(value: List<PersonsDTO>) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String): List<PersonsDTO> {
        return Gson().fromJson(value, Array<PersonsDTO>::class.java).toList()
    }

    @TypeConverter
    fun movieListToJson(movie: Movies) = Gson().toJson(movie)

    @TypeConverter
    fun jsonToEntity(value: String): Movies {
        return Gson().fromJson(value, Movies::class.java)
    }
}