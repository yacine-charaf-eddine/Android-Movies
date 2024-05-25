package com.example.moviesapp.data.domain

import com.example.moviesapp.data.api.Movie
import com.example.moviesapp.data.api.MoviesListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("discover/movie")
    suspend fun fetchMovies(@Query("api_key") apiKey: String): Response<MoviesListResponse<Movie>>
}