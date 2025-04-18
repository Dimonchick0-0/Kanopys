package com.example.kanopys.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kanopys.data.network.ApiFactory
import com.example.kanopys.data.repository.RepositoryImpl
import com.example.kanopys.domain.entity.Movie
import com.example.kanopys.presentation.state.StateMovie
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieViewModel @Inject constructor(
    private val repositoryImpl: RepositoryImpl
) : ViewModel() {

    val channelMovie = Channel<Int>()

    private var _state = MutableStateFlow<StateMovie>(
        StateMovie.InitialState
    )

    val state = _state.asStateFlow()

    init {
        channelMovie.receiveAsFlow().onEach {
            _state.emit(StateMovie.LoadingMovie)
        }.map {
            getMovieFromChannel(it)
            getMovieById(it).onEach { movie ->
                _state.emit(StateMovie.AddMovieToFavorite(movie))
                _state.emit((StateMovie.DeleteMovieFromFavorite(movie)))
            }.launchIn(viewModelScope)
        }.launchIn(viewModelScope)
    }

    private fun getMovieFromChannel(id: Int) {
        getMovieById(id).onEach {
            _state.emit(StateMovie.UploadedMovie(it))
        }.launchIn(viewModelScope)
    }

    private fun getMovieById(id: Int): Flow<Movie> {
        return flow {
            emit(ApiFactory.apiService.getMovieById(id))
        }
    }

    fun checkMovieFavoriteById(id: Int) = flow {
        emit(repositoryImpl.checkMovieFavorite(id))
    }

    suspend fun addMovieToFavorite(item: Movie) {
        repositoryImpl.addingToFavorites(item)
    }

    suspend fun deleteMovieFromFavorite(id: Int) {
        repositoryImpl.deleteFromFavorite(id)
    }
}