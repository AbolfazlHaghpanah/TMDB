package com.hooshang.tmdb.feature.detail.data.network.api

import com.hooshang.tmdb.feature.detail.data.model.remote.MovieDetailResponse
import com.hooshang.tmdb.core.utils.tmdpApiKey
import com.hooshang.tmdb.feature.detail.data.network.response.MovieDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DetailApi {

    @GET("movie/{id}")
    suspend fun getMovieDetail(
        @Path("id") id: Int,
        @Query("append_to_response") append: String = "external_ids,credits,similar"
    ): Response<MovieDetailResponse>
}