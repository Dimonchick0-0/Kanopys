package com.example.kanopys.data.network

import com.example.kanopys.domain.entity.Movie
import com.example.kanopys.domain.entity.Movies
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/v1.4/movie/{id}")
    suspend fun getMovieById(@Path("id") id: Int): Movie

    @GET("/v1.4/movie/search")
    suspend fun getListMovie(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("query") title: String
    ): Response<Movies>
}