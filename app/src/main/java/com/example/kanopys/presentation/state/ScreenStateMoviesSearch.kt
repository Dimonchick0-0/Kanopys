package com.example.kanopys.presentation.state

import com.example.kanopys.domain.entity.Movies

sealed interface ScreenStateMoviesSearch {
    data object InitialState: ScreenStateMoviesSearch

    data object Loading: ScreenStateMoviesSearch

    data object NotFoundMovies: ScreenStateMoviesSearch

    data class LoadedMovies(val movie: Movies): ScreenStateMoviesSearch

    data object InternetError: ScreenStateMoviesSearch
}