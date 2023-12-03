package com.example.tmdb.feature.home.data.remote.remotedatasource

import com.example.tmdb.core.network.Result
import com.example.tmdb.core.network.safeApi
import com.example.tmdb.feature.home.data.homeSafeApi
import com.example.tmdb.feature.home.data.local.entity.NowPlayingEntity
import com.example.tmdb.feature.home.data.remote.HomeApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRemoteDataSource @Inject constructor(
    private val homeApi: HomeApi
) {

    suspend fun getGenres(): Result {
        return homeSafeApi { homeApi.getGenre() }
    }

    suspend fun getNowPlaying(): Result {
        return homeSafeApi { homeApi.getNowPlaying() }
    }

    suspend fun getTopMovie(): Result {
        return homeSafeApi { homeApi.getTopRated() }
    }

    suspend fun getPopular(): Result {
        return homeSafeApi { homeApi.getMostPopular() }
    }
}