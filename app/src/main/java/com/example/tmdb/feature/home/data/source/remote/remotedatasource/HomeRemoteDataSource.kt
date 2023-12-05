package com.example.tmdb.feature.home.data.source.remote.remotedatasource

import com.example.tmdb.core.data.source.remote.bodyOrThrow
import com.example.tmdb.feature.home.data.source.remote.api.HomeApi
import com.example.tmdb.feature.home.data.model.remote.GenreResponse
import com.example.tmdb.feature.home.data.model.remote.MovieResponse
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