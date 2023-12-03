package com.example.tmdb.feature.home.data.repository

import com.example.tmdb.core.network.Result
import com.example.tmdb.feature.home.data.local.localdatasource.HomeLocalDataSource
import com.example.tmdb.feature.home.data.local.relation.crossref.TopMovieGenreCrossRef
import com.example.tmdb.feature.home.data.remote.json.GenreResponse
import com.example.tmdb.feature.home.data.remote.json.MovieResponse
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
        val result = remoteDataSource.getGenres()
        if (result is Result.Success<*>) {
            val data = result.response as GenreResponse
            localDataSource.storeGenres(data.genres.map { it.toGenreEntity() })
        }
    }

    suspend fun fetchNowPlaying() = withContext(Dispatchers.IO) {
        val result = remoteDataSource.getNowPlaying()
        if (result is Result.Success<*>) {
            val data = result.response as MovieResponse
            data.results.forEach {
                localDataSource.addNowPlaying(it.toNowPlayingEntity(), it.toMovieEntity())
            }
        }
    }

    suspend fun fetchTopMovie() = withContext(Dispatchers.IO) {
        val result = remoteDataSource.getTopMovie()
        if (result is Result.Success<*>) {
            val data = result.response as MovieResponse
            data.results.forEach {
                localDataSource.storeTopMovie(
                    it.toTopPlayingEntity(),
                    it.toMovieEntity(),
                    it.genreIds ?: listOf()
                )
            }
        }
    }

    suspend fun fetchPopularMovie() = withContext(Dispatchers.IO) {
        val result = remoteDataSource.getPopular()
        if (result is Result.Success<*>) {
            val data = result.response as MovieResponse
            data.results.forEach {
                localDataSource.storePopularMovie(
                    it.toPopularMovieEntity(),
                    it.toMovieEntity(),
                    it.genreIds ?: listOf()
                )
            }
        }
    }
}