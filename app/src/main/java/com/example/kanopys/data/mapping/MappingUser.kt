package com.example.kanopys.data.mapping

import com.example.kanopys.data.database.MovieDatabase
import com.example.kanopys.data.database.UserDatabase
import com.example.kanopys.domain.entity.Movie
import com.example.kanopys.domain.entity.User
import javax.inject.Inject

class MappingUser @Inject constructor() {
    fun mapEntityUserToDatabaseUser(user: User): UserDatabase {
        return UserDatabase(
            id = user.id,
            displayName = user.displayName,
            password = user.password,
            email = user.email
        )
    }

    fun mapEntityMovieToDBMovie(movie: Movie): MovieDatabase {
        return MovieDatabase(
            id = movie.id,
            name = movie.name,
            year = movie.year,
            poster = movie.poster,
            rating = movie.rating,
            description = movie.description,
            persons = movie.persons
        )
    }
}