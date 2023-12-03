package com.example.tmdb.feature.home.data.remote.remotedatasource

import com.example.tmdb.core.data.common.bodyOrThrow
import com.example.tmdb.feature.home.data.remote.HomeApi
import com.example.tmdb.feature.home.data.remote.json.GenreResponse
import com.example.tmdb.feature.home.data.remote.json.MovieResponse
import javax.inject.Inject

class HomeRemoteDataSource @Inject constructor(
    private val homeApi: HomeApi
) {

    suspend fun getGenres(): GenreResponse {
        return bodyOrThrow { homeApi.getGenre() }
    }

    suspend fun getNowPlaying(): MovieResponse {
        return bodyOrThrow { homeApi.getNowPlaying() }
    }

    suspend fun getTopMovie(): MovieResponse {
        return bodyOrThrow { homeApi.getTopRated() }
    }

    suspend fun getPopular(): MovieResponse {
        return bodyOrThrow { homeApi.getMostPopular() }
    }
}