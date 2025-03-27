package com.example.kanopys.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.kanopys.data.network.ApiFactory
import com.example.kanopys.domain.entity.Movies
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchViewModel @Inject constructor(): ViewModel() {
    fun searchMovie(page: Int, limit: Int, title: String): Flow<Movies> {
        return flow {
            emit(ApiFactory.apiService.getListMovie(page, limit, title))
        }
    }
}