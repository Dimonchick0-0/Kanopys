package com.example.kanopys.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kanopys.data.network.ApiFactory
import com.example.kanopys.data.repository.RepositoryImpl
import com.example.kanopys.domain.entity.Movie
import com.example.kanopys.domain.entity.Movies
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import javax.inject.Inject

class MovieScreenViewModel @Inject constructor(
    private val repositoryImpl: RepositoryImpl
) : ViewModel() {
    fun loadAllFavoriteMovies(): Flow<List<Movie>> {
        return flow { emitAll(repositoryImpl.loadAllFavoriteMovies()) }
    }
}