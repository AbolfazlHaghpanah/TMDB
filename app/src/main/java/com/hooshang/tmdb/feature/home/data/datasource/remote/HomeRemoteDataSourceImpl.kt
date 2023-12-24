package com.hooshang.tmdb.feature.home.data.datasource.remote

import com.hooshang.tmdb.core.data.source.remote.bodyOrThrow
import com.hooshang.tmdb.feature.home.data.network.HomeApi
import com.hooshang.tmdb.feature.home.data.network.model.GenreResponse
import com.hooshang.tmdb.feature.home.data.network.model.MovieResponse
import javax.inject.Inject

class HomeRemoteDataSourceImpl @Inject constructor(
    private val homeApi: HomeApi
) : HomeRemoteDataSource {

    suspend fun getGenres(): GenreResponse = bodyOrThrow { homeApi.getGenre() }

    suspend fun getNowPlaying(): MovieResponse = bodyOrThrow { homeApi.getNowPlaying() }

    suspend fun getTopMovie(): MovieResponse = bodyOrThrow { homeApi.getTopRated() }

    suspend fun getPopular(): MovieResponse = bodyOrThrow { homeApi.getMostPopular() }
}