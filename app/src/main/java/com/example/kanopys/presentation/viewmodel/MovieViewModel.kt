package com.example.kanopys.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.kanopys.data.network.ApiFactory
import com.example.kanopys.data.repository.RepositoryImpl
import com.example.kanopys.domain.entity.Movie
import com.example.kanopys.domain.entity.Movies
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieViewModel @Inject constructor(
    private val repositoryImpl: RepositoryImpl
) : ViewModel() {
    fun getMovieById(id: Int): Flow<Movie> {
        return flow {
            emit(ApiFactory.apiService.getMovieById(id))
        }
    }

    suspend fun addMovieToFavorite(item: Movie) {
        repositoryImpl.addingToFavorites(item)
    }
}