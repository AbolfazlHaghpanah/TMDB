package com.example.tmdb.feature.home.network

import com.example.tmdb.feature.home.network.json.NowPlayingResponse
import kotlinx.serialization.json.Json
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface HomeApi {

    @GET("3/movie/now_playing")
    suspend fun getNowPlaying(
        @Header("Authorization") apiKey: String = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxYWQ0YTgxZGZlOWY1ODlhMTIwZTIwNzE5MmFlY2E3MSIsInN1YiI6IjY1NGIyYTU0MzkxYjljMDExZDMwNWI2MiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Pf8Q1FtyfF0qoH2H_YvdiBhxsFUUwEKnQ0cG1dKCe2Q"
    ): Response<NowPlayingResponse>

    @GET("/3/movie/popular")
    suspend fun getMostPopular(
        @Header("Authorization") apiKey: String = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxYWQ0YTgxZGZlOWY1ODlhMTIwZTIwNzE5MmFlY2E3MSIsInN1YiI6IjY1NGIyYTU0MzkxYjljMDExZDMwNWI2MiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Pf8Q1FtyfF0qoH2H_YvdiBhxsFUUwEKnQ0cG1dKCe2Q"
    ): Response<NowPlayingResponse>

    @GET("/3/movie/top_rated")
    suspend fun getTopRated(
        @Header("Authorization") apiKey: String = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxYWQ0YTgxZGZlOWY1ODlhMTIwZTIwNzE5MmFlY2E3MSIsInN1YiI6IjY1NGIyYTU0MzkxYjljMDExZDMwNWI2MiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Pf8Q1FtyfF0qoH2H_YvdiBhxsFUUwEKnQ0cG1dKCe2Q"
    ): Response<NowPlayingResponse>
}