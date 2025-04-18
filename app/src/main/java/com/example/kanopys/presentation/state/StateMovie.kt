package com.example.kanopys.presentation.state

import com.example.kanopys.domain.entity.Movie

sealed interface StateMovie {
    data object InitialState: StateMovie

    data object LoadingMovie: StateMovie

    data class UploadedMovie(val movie: Movie): StateMovie

    data class AddMovieToFavorite(val movie: Movie): StateMovie

    data class DeleteMovieFromFavorite(val movie: Movie): StateMovie
}