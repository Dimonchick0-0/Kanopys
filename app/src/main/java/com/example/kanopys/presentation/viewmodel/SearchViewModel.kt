package com.example.kanopys.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kanopys.data.network.ApiFactory
import com.example.kanopys.domain.entity.Movies
import com.example.kanopys.presentation.state.ScreenStateMoviesSearch
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.retry
import retrofit2.Response
import javax.inject.Inject

class SearchViewModel @Inject constructor() : ViewModel() {

    val channel = Channel<String>()

    private val _state = MutableStateFlow<ScreenStateMoviesSearch>(
        ScreenStateMoviesSearch.InitialState
    )
    val state = _state.asStateFlow()

    init {
        channel.receiveAsFlow().onEach {
            _state.emit(ScreenStateMoviesSearch.Loading)
        }.map {
            getMovie(it)
        }.retry {
            _state.emit(ScreenStateMoviesSearch.InternetError)
            true
        }.launchIn(viewModelScope)
    }

    private suspend fun getMovie(query: String) {
        val result = searchMovie(1, 1, query)
        result.body()?.let { movies ->
            _state.emit(ScreenStateMoviesSearch.LoadedMovies(movies))
            if (movies.docs.isEmpty()) {
                _state.emit(ScreenStateMoviesSearch.NotFoundMovies)
            }
        }
    }

    private suspend fun searchMovie(page: Int, limit: Int, title: String): Response<Movies> {
        return ApiFactory.apiService.getListMovie(page, limit, title)
    }

    companion object {
        private const val TAG = "TESTFLOW"
    }
}