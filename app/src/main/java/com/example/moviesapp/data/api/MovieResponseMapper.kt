package com.example.moviesapp.data.api

import kotlinx.serialization.json.Json
import retrofit2.Response

class MovieResponseMapper {
    fun <ITEM> processListResponse(response: Response<MoviesListResponse<ITEM>>): UniversalResult<ITEM> {
        val message: String

        val universalResult: UniversalResult<ITEM> = UniversalResult(response.code(), response.message(), null, listOf())
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
        return universalResult
    }
}