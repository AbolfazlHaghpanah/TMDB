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
        return bodyOrThrow { homeApi.getGenres() }
    }

    override suspend fun getNowPlayingMovies(): MovieResponse {
        return bodyOrThrow { homeApi.getNowPlayingMovies() }
    }

    override suspend fun getTopMovies(): MovieResponse {
        return bodyOrThrow { homeApi.getTopMovies() }
    }

    override suspend fun getMostPopularMovies(): MovieResponse {
        return bodyOrThrow { homeApi.getMostPopularMovies() }
    }
}