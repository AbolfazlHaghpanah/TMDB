package com.hooshang.tmdb.feature.home.domain.repository

import com.hooshang.tmdb.feature.home.domain.model.HomeMovieDomainModel
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getNowPlaying(): Flow<List<HomeMovieDomainModel>>
    suspend fun getTopMovie(): Flow<List<HomeMovieDomainModel>>
    suspend fun getPopularMovie(): Flow<List<HomeMovieDomainModel>>
    suspend fun fetchNowPlaying()
    suspend fun fetchTopMovie()
    suspend fun fetchPopularMovie()
    suspend fun fetchGenres()
}