package com.example.moviesapp.data.source

import com.example.moviesapp.data.source.models.Movie
import com.example.moviesapp.data.api.UniversalResult
import kotlinx.coroutines.flow.Flow

interface MoviesDataSource {
    suspend fun fetchMovies(page: Int): Flow<UniversalResult<Movie>>
    suspend fun fetchMovie(id: String): Flow<UniversalResult<Movie>>
}