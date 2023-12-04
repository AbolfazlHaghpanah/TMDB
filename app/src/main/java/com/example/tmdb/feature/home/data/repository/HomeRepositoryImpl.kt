package com.example.tmdb.feature.home.data.repository

import com.example.tmdb.feature.home.data.local.localdatasource.HomeLocalDataSource
import com.example.tmdb.feature.home.data.remote.remotedatasource.HomeRemoteDataSource
import com.example.tmdb.feature.home.domain.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val localDataSource: HomeLocalDataSource,
    private val remoteDataSource: HomeRemoteDataSource
) : HomeRepository {

    override suspend fun getNowPlaying() = withContext(Dispatchers.IO) {
        localDataSource.getNowPlaying()
    }

    override suspend fun getTopMovie() = withContext(Dispatchers.IO) {
        localDataSource.getTopMovie()
    }

    override suspend fun getPopularMovie() = withContext(Dispatchers.IO) {
        localDataSource.getPopularMovie()
    }

    override suspend fun fetchGenres() = withContext(Dispatchers.IO) {
        localDataSource.storeGenres(
            remoteDataSource.getGenres().genres.map { it.toGenreEntity() }
        )
    }

    override suspend fun fetchNowPlaying() = withContext(Dispatchers.IO) {
        val data = remoteDataSource.getNowPlaying()
        data.results.forEach {
            localDataSource.addNowPlaying(
                it.toNowPlayingEntity(),
                it.toMovieEntity()
            )
        }
    }

    override suspend fun fetchTopMovie() = withContext(Dispatchers.IO) {
        val data = remoteDataSource.getTopMovie()
        data.results.forEach {
            localDataSource.storeTopMovie(
                it.toTopPlayingEntity(),
                it.toMovieEntity(),
                it.genreIds ?: listOf()
            )
        }
    }

    override suspend fun fetchPopularMovie() = withContext(Dispatchers.IO) {
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

