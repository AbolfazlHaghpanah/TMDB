package com.example.tmdb.feature.home.data.source.remote.api

import com.example.tmdb.feature.home.data.model.remote.GenreResponse
import com.example.tmdb.feature.home.data.model.remote.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import com.example.tmdb.core.utils.tmdpApiKey


interface HomeApi {
    @GET("genre/movie/list?language=en")
    suspend fun getGenre(
        @Header("Authorization") apiKey: String = tmdpApiKey
    ):Response<GenreResponse>

    @GET("movie/now_playing")
    suspend fun getNowPlaying(
        @Header("Authorization") apiKey: String = tmdpApiKey
    ): Response<MovieResponse>

    @GET("movie/popular")
    suspend fun getMostPopular(
        @Header("Authorization") apiKey: String = tmdpApiKey
    ): Response<MovieResponse>

    @GET("movie/top_rated")
    suspend fun getTopRated(
        @Header("Authorization") apiKey: String = tmdpApiKey
    ): Response<MovieResponse>
}