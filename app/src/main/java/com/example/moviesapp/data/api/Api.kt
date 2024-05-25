package com.example.moviesapp.data.api

object Api {
    // i probably should obtain this from tmdb and save it somewhere locally but im too lazy
    private const val BASE_POSTER_PATH = "https://image.tmdb.org/t/p/w500"
    fun getPosterPath(posterPath: String): String {
        return BASE_POSTER_PATH + posterPath
    }
}