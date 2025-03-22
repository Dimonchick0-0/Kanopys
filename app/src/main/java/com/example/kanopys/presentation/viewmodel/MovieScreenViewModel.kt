package com.example.kanopys.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.kanopys.data.network.ApiFactory
import com.example.kanopys.domain.entity.Movie
import com.example.kanopys.domain.entity.Movies
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import javax.inject.Inject

class MovieScreenViewModel @Inject constructor() : ViewModel() {
    suspend fun getMovieById(id: Int): Movie {
        return withContext(Dispatchers.Default) {
            return@withContext ApiFactory.apiService.getMovieById(id)
        }
    }

    suspend fun searchMovie(page: Int, limit: Int, title: String): Movies {
        return ApiFactory.apiService.getListMovie(page, limit, title)
    }
}