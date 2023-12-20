package com.hooshang.tmdb.feature.home.data.datasource.remote

import com.hooshang.tmdb.feature.home.data.network.model.GenreResponse
import com.hooshang.tmdb.feature.home.data.network.model.MovieResponse

interface HomeRemoteDataSource {
    suspend fun getGenres(): GenreResponse
    suspend fun getNowPlaying(): MovieResponse
    suspend fun getTopMovie(): MovieResponse
    suspend fun getPopular(): MovieResponse
}