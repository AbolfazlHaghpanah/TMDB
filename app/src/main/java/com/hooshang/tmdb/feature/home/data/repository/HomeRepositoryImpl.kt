package com.hooshang.tmdb.feature.home.data.repository

import com.hooshang.tmdb.feature.home.data.model.local.relation.crossref.PopularMovieGenreCrossRef
import com.hooshang.tmdb.feature.home.data.model.local.relation.crossref.TopMovieGenreCrossRef
import com.hooshang.tmdb.feature.home.data.source.local.localdatasource.HomeLocalDataSource
import com.hooshang.tmdb.feature.home.data.source.remote.remotedatasource.HomeRemoteDataSource
import com.hooshang.tmdb.feature.home.domain.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val localDataSource: HomeLocalDataSource,
    private val remoteDataSource: HomeRemoteDataSource
) : HomeRepository {

    override suspend fun getNowPlaying() = withContext(Dispatchers.IO) {
        localDataSource.getNowPlayings()
    }

    override suspend fun getTopMovie() = withContext(Dispatchers.IO) {
        localDataSource.getTopMovie()
    }

    override suspend fun getPopularMovie() = withContext(Dispatchers.IO) {
        localDataSource.getPopularMovies()
    }

    override suspend fun fetchGenres() = withContext(Dispatchers.IO) {
        localDataSource.insertGenres(
            remoteDataSource.getGenres().genres.map { it.toGenreEntity() }
        )
    }

    override suspend fun fetchNowPlaying() = withContext(Dispatchers.IO) {
        val data = remoteDataSource.getNowPlaying().results

        localDataSource.getNowPlayings()
            .onEach { localMovies ->
                if (localMovies.map { it.movieId }.compare(data.map { it.id })) {
                    localDataSource.removeNowPlayingMovies()
                }
            }
            .first()

        localDataSource.insertMovies(
            data.map { it.toMovieEntity() }
        )

        localDataSource.insertNowPlayingMovies(
            data.map { it.toNowPlayingEntity() }
        )
    }

    override suspend fun fetchTopMovie() = withContext(Dispatchers.IO) {
        val data = remoteDataSource.getTopMovie().results

        localDataSource.getTopMovie()
            .onEach { localMovies ->
                if (localMovies.map { it.movieId }.compare(data.map { it.id })) {
                    localDataSource.removeTopMovies()
                }
            }
            .first()

        localDataSource.insertMovies(
            data.map { it.toMovieEntity() }
        )

        localDataSource.insertTopMovies(
            data.map { movie ->
                localDataSource.insertTopMoviesGenres(
                    movie.genreIds.orEmpty().map { genreId ->
                        TopMovieGenreCrossRef(
                            movieId = movie.id,
                            genreId = genreId
                        )
                    }
                )
                movie.toTopPlayingEntity()
            }
        )
    }

    override suspend fun fetchPopularMovie() = withContext(Dispatchers.IO) {
        val data = remoteDataSource.getPopular().results

        localDataSource.getPopularMovies()
            .onEach { localMovies ->
                if (localMovies.map { it.movieId }.compare(data.map { it.id })) {
                    localDataSource.removePopularMovies()
                }
            }
            .first()

        localDataSource.insertMovies(
            data.map {
                it.toMovieEntity()
            }
        )

        localDataSource.insertPopularMovies(
            data.map { movie ->
                localDataSource.insertPopularMoviesGenres(
                    movie.genreIds.orEmpty().map { genreId ->
                        PopularMovieGenreCrossRef(
                            movieId = movie.id,
                            genreId = genreId
                        )
                    }
                )
                movie.toPopularMovieEntity()
            }
        )
    }

    private fun List<Int>.compare(currentData: List<Int>): Boolean =
        !(this.size == currentData.size && this.toSet() == currentData.toSet())
}

