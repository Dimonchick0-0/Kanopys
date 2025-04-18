package com.example.kanopys.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kanopys.data.network.ApiFactory
import com.example.kanopys.data.repository.RepositoryImpl
import com.example.kanopys.domain.entity.Movie
import com.example.kanopys.domain.entity.Movies
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import javax.inject.Inject

class MovieScreenViewModel @Inject constructor(
    private val repositoryImpl: RepositoryImpl
) : ViewModel() {

    fun loadAllFavoriteMovies(): Flow<List<Movie>> {
        return flow {
            emitAll(repositoryImpl.loadAllFavoriteMovies())
        }
    }
}