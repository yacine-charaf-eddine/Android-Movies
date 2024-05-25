package com.example.moviesapp.data.source.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movie(

    @SerialName("id")
    val id: Int,

    @SerialName("title")
    val title: String = "no title found",

    @SerialName("overview")
    val overview: String = "no overview found",


    @SerialName("release_date")
    val releaseDate: String = "N/A",

    @SerialName("poster_path")
    val posterPath: String = "",

    @SerialName("vote_average")
    val voteAverage: Double = 0.0,

    @SerialName("vote_count")
    val voteCount: Int = 0,

    @SerialName("genres")
    val genres: List<Genre> = emptyList(),

    )

@Serializable
data class Genre(
    val id: Int,
    val name: String
)