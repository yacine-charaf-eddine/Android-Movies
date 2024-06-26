package com.example.moviesapp.data.source

import com.example.moviesapp.data.source.models.Movie
import com.example.moviesapp.data.api.MovieResponseMapper
import com.example.moviesapp.data.source.models.MoviesListResponse
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

    override suspend fun fetchMovies(page: Int): Flow<UniversalResult<Movie>> {
        val moviesResponse: Response<MoviesListResponse<Movie>> = apiService.fetchMovies(page)
        return flow {emit(mapper.processListResponse(moviesResponse))}
    }

    override suspend fun fetchMovie(id: String): Flow<UniversalResult<Movie>> {
        val movieResponse: Response<Movie> = apiService.fetchMovie(id)
        return flow {emit(mapper.processDetailResponse(movieResponse))}
    }
}