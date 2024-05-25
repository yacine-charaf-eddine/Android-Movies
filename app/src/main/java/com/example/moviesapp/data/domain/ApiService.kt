package com.example.moviesapp.data.domain

import com.example.moviesapp.data.api.Movie
import com.example.moviesapp.data.api.MoviesListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("3/discover/movie")
    suspend fun fetchMovies(): Response<MoviesListResponse<Movie>>
    @GET("3/movie/{id}")
    suspend fun fetchMovie(@Path("id") id: String): Response<Movie>

}