package com.example.tmdb.feature.home.network

import com.example.tmdb.feature.home.network.json.GenreResponse
import com.example.tmdb.feature.home.network.json.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import com.example.tmdb.core.utils.tmdpApiKey


interface HomeApi {
    @GET("3/genre/movie/list?language=en")
    suspend fun getGenre(
        @Header("Authorization") apiKey: String = tmdpApiKey
    ):Response<GenreResponse>

    @GET("3/movie/now_playing")
    suspend fun getNowPlaying(
        @Header("Authorization") apiKey: String = tmdpApiKey
    ): Response<MovieResponse>

    @GET("/3/movie/popular")
    suspend fun getMostPopular(
        @Header("Authorization") apiKey: String = tmdpApiKey
    ): Response<MovieResponse>

    @GET("/3/movie/top_rated")
    suspend fun getTopRated(
        @Header("Authorization") apiKey: String = tmdpApiKey
    ): Response<MovieResponse>
}