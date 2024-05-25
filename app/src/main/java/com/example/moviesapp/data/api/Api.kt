package com.example.moviesapp.data.api

object Api {
    private const val BASE_POSTER_PATH = "https://image.tmdb.org/t/p/w500"
    fun getPosterPath(posterPath: String): String {
        return BASE_POSTER_PATH + posterPath
    }
}