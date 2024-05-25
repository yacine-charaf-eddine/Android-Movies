package com.example.moviesapp.data.source

import com.example.moviesapp.data.source.models.Movie
import com.example.moviesapp.data.api.UniversalResult
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoviesRepository @Inject constructor(private var moviesDataSource: MoviesDataSource):
    MoviesDataSource {

    override suspend fun fetchMovies(page: Int): Flow<UniversalResult<Movie>> {
        return withContext(IO){
           return@withContext moviesDataSource.fetchMovies(page)
        }

    }

    override suspend fun fetchMovie(id: String): Flow<UniversalResult<Movie>> {
        return withContext(IO){
            return@withContext moviesDataSource.fetchMovie(id)
        }
    }
}