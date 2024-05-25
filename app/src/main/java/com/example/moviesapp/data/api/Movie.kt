package com.example.moviesapp.data.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movie(

    @SerialName("title")
    val title: String,

    @SerialName("overview")
    val overview: String,


    @SerialName("release_date")
    val releaseDate: String,

    @SerialName("poster_path")
    val posterPath: String


)