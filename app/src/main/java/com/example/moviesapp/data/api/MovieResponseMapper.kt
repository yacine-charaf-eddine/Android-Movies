package com.example.moviesapp.data.api

import com.example.moviesapp.data.source.models.MoviesListResponse
import retrofit2.Response

class MovieResponseMapper {
    fun <ITEM> processListResponse(response: Response<MoviesListResponse<ITEM>>): UniversalResult<ITEM> {
        val message: String

        val universalResult: UniversalResult<ITEM> = UniversalResult(response.code(), response.message(), null, listOf(), 0)
        if (!response.isSuccessful) {
            message = if (response.code() == 500) {
                "Server has encountered an issue."
            } else {
                "API Error " + response.code() + " : " + response.message()
            }
            universalResult.message = message
            return universalResult
        }
        val body: MoviesListResponse<ITEM>? = response.body()
        if (body == null || body.data.isNullOrEmpty()) {
            universalResult.message = "no movies found"
            return universalResult
        }
        universalResult.setItems(body.data)
        universalResult.setCode(response.code())
        universalResult.setCurrentPage(body.page)
        return universalResult
    }

    fun <ITEM> processDetailResponse(response: Response<ITEM>): UniversalResult<ITEM> {
        val message: String

        val universalResult: UniversalResult<ITEM> = UniversalResult(response.code(), response.message(), null, listOf(), 0)
        if (!response.isSuccessful) {
            message = if (response.code() == 500) {
                "Server has encountered an issue."
            } else {
                "API Error " + response.code() + " : " + response.message()
            }
            universalResult.message = message
            return universalResult
        }
        val body: ITEM? = response.body()
        if (body == null) {
            universalResult.message = "no movies found"
            return universalResult
        }
        universalResult.setItem(body)
        universalResult.setCode(response.code())
        return universalResult
    }
}