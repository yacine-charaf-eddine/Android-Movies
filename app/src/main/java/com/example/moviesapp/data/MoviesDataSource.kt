package com.example.moviesapp.data

import com.example.moviesapp.data.api.Movie
import com.example.moviesapp.data.api.UniversalResult
import kotlinx.coroutines.flow.Flow

interface MoviesDataSource {
    suspend fun fetchMovies(): Flow<UniversalResult<Movie>>
    suspend fun fetchMovie(id: String): Flow<UniversalResult<Movie>>
}