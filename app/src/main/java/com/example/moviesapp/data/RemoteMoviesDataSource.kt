package com.example.moviesapp.data

import com.example.moviesapp.data.api.Movie
import com.example.moviesapp.data.api.MovieResponseMapper
import com.example.moviesapp.data.api.MoviesListResponse
import com.example.moviesapp.data.api.UniversalResult
import com.example.moviesapp.data.domain.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class RemoteMoviesDataSource @Inject constructor(
    private var apiService: ApiService,
    private var mapper: MovieResponseMapper
): MoviesDataSource {

    override suspend fun fetchMovies(): Flow<UniversalResult<Movie>> {
        val moviesResponse: Response<MoviesListResponse<Movie>> = apiService.fetchMovies("c9856d0cb57c3f14bf75bdc6c063b8f3")
        return flow {emit(mapper.processListResponse(moviesResponse))}
    }
}