package com.hooshang.tmdb.feature.home.data.source.remote.api

import com.hooshang.tmdb.feature.home.data.model.remote.GenreResponse
import com.hooshang.tmdb.feature.home.data.model.remote.MovieResponse
import retrofit2.Response
import retrofit2.http.GET


interface HomeApi {
    @GET("genre/movie/list?language=en")
    suspend fun getGenre(): Response<GenreResponse>

    @GET("movie/now_playing")
    suspend fun getNowPlaying(): Response<MovieResponse>

    @GET("movie/popular")
    suspend fun getMostPopular(): Response<MovieResponse>

    @GET("movie/top_rated")
    suspend fun getTopRated(): Response<MovieResponse>
}