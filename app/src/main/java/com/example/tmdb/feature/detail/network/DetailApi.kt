package com.example.tmdb.feature.detail.network

import com.example.tmdb.core.utils.tmdpApiKey
import com.example.tmdb.feature.detail.network.json.MovieDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface DetailApi {

    @GET("movie/{id}")
    suspend fun getMovieDetail(
        @Header("Authorization") apiKey: String = tmdpApiKey,
        @Path("id") id: Int = 550,
        @Query("append_to_response") append: String = "external_ids,credits,similar"
    ): Response<MovieDetail>

}