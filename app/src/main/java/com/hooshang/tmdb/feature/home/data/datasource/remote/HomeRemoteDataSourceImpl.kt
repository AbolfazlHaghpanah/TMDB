package com.hooshang.tmdb.feature.home.data.datasource.remote

import com.hooshang.tmdb.core.data.source.remote.bodyOrThrow
import com.hooshang.tmdb.feature.home.data.network.HomeApi
import com.hooshang.tmdb.feature.home.data.network.model.GenreResponse
import com.hooshang.tmdb.feature.home.data.network.model.MovieResponse
import javax.inject.Inject

class HomeRemoteDataSourceImpl @Inject constructor(
    private val homeApi: HomeApi
) : HomeRemoteDataSource {

    override suspend fun getGenres(): GenreResponse {
        return bodyOrThrow { homeApi.getGenre() }
    }

    override suspend fun getNowPlaying(): MovieResponse {
        return bodyOrThrow { homeApi.getNowPlaying() }
    }

    override suspend fun getTopMovie(): MovieResponse {
        return bodyOrThrow { homeApi.getTopRated() }
    }

    override suspend fun getPopular(): MovieResponse {
        return bodyOrThrow { homeApi.getMostPopular() }
    }
}