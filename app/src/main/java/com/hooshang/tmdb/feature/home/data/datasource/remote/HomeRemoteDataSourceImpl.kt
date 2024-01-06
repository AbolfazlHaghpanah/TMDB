package com.hooshang.tmdb.feature.home.data.datasource.remote

import com.hooshang.tmdb.core.data.network.common.bodyOrThrow
import com.hooshang.tmdb.feature.home.data.network.HomeApi
import com.hooshang.tmdb.feature.home.data.network.model.GenreResponse
import com.hooshang.tmdb.feature.home.data.network.model.MovieResponse
import javax.inject.Inject

class HomeRemoteDataSourceImpl @Inject constructor(
    private val homeApi: HomeApi
) : HomeRemoteDataSource {

    override suspend fun getGenres(): GenreResponse = bodyOrThrow { homeApi.getGenres() }

    override suspend fun getNowPlayingMovies(): MovieResponse =
        bodyOrThrow { homeApi.getNowPlayingMovies() }

    override suspend fun getMostPopularMovies(): MovieResponse =
        bodyOrThrow { homeApi.getMostPopularMovies() }

    override suspend fun getTopMovies(): MovieResponse =
        bodyOrThrow { homeApi.getTopMovies() }
}