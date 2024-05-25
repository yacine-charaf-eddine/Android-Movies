package com.example.moviesapp.data.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class MoviesListResponse<T>(
    @SerialName("page")
    val page: Int?,

    @SerialName("results")
    val data: List<T>?,

    @SerialName("total_pages")
    val totalPages: Int?
)