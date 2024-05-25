package com.example.moviesapp.data.domain

import com.example.moviesapp.data.source.models.Movie
import com.example.moviesapp.data.source.models.MoviesListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("3/discover/movie")
    suspend fun fetchMovies(@Query("page") page: Int): Response<MoviesListResponse<Movie>>
    @GET("3/movie/{id}")
    suspend fun fetchMovie(@Path("id") id: String): Response<Movie>

}