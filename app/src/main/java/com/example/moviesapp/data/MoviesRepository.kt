package com.example.moviesapp.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MoviesRepository @Inject constructor(): MoviesDataSource {

    val movies: Flow<List<String>> = flow {
        emit(listOf("Movie 1", "Movie 2", "Movie 3"))
    }
    override suspend fun fetchMovies(): Flow<List<String>> {
        return flow {
            emit(listOf("Movie 1", "Movie 2", "Movie 3"))
        }
    }
}