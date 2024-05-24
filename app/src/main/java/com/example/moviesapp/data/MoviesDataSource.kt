package com.example.moviesapp.data

import kotlinx.coroutines.flow.Flow

interface MoviesDataSource {
    suspend fun fetchMovies(): Flow<List<String>>
}