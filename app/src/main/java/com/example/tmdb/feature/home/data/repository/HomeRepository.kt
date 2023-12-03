package com.example.tmdb.feature.home.data.repository

import com.example.tmdb.feature.home.data.local.localdatasource.HomeLocalDataSource
import com.example.tmdb.feature.home.data.remote.remotedatasource.HomeRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val localDataSource: HomeLocalDataSource,
    private val remoteDataSource: HomeRemoteDataSource
) {
    suspend fun observeNowPlaying() = withContext(Dispatchers.IO) {
        localDataSource.getNowPlaying()
    }

    suspend fun observeTopMovie() = withContext(Dispatchers.IO) {
        localDataSource.getTopMovie()
    }

    suspend fun observePopularMovie() = withContext(Dispatchers.IO) {
        localDataSource.getPopularMovie()
    }

    suspend fun fetchGenres() = withContext(Dispatchers.IO) {
        localDataSource.storeGenres(
            remoteDataSource.getGenres().genres.map { it.toGenreEntity() }
        )
    }

    suspend fun fetchNowPlaying() = withContext(Dispatchers.IO) {
        val data = remoteDataSource.getNowPlaying()
        data.results.forEach {
            localDataSource.addNowPlaying(
                it.toNowPlayingEntity(),
                it.toMovieEntity()
            )
        }
    }

    suspend fun fetchTopMovie() = withContext(Dispatchers.IO) {
        val data = remoteDataSource.getTopMovie()
        data.results.forEach {
            localDataSource.storeTopMovie(
                it.toTopPlayingEntity(),
                it.toMovieEntity(),
                it.genreIds ?: listOf()
            )
        }
    }

    suspend fun fetchPopularMovie() = withContext(Dispatchers.IO) {
        val data = remoteDataSource.getPopular()
        data.results.forEach {
            localDataSource.storePopularMovie(
                it.toPopularMovieEntity(),
                it.toMovieEntity(),
                it.genreIds ?: listOf()
            )
        }
    }
}