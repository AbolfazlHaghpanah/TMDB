package com.example.tmdb.feature.detail.network

import com.example.tmdb.feature.detail.network.json.MovieDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface DetailApi {

    @GET("3/movie/{id}?append_to_response=external_ids'")
    suspend fun getMovieDetail(
        @Header("Authorization") apiKey: String = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxYWQ0YTgxZGZlOWY1ODlhMTIwZTIwNzE5MmFlY2E3MSIsInN1YiI6IjY1NGIyYTU0MzkxYjljMDExZDMwNWI2MiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Pf8Q1FtyfF0qoH2H_YvdiBhxsFUUwEKnQ0cG1dKCe2Q",
        @Path("id") id: Int = 550,
        @Query("language") language: String = "en-US",
        @Query("append_to_response") append: String = "external_ids,credits,similar"
    ): Response<MovieDetail>

}