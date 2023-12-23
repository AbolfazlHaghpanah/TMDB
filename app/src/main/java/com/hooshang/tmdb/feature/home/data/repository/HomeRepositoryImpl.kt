package com.hooshang.tmdb.feature.home.data.repository

import com.hooshang.tmdb.feature.home.data.datasource.local.HomeLocalDataSource
import com.hooshang.tmdb.feature.home.data.datasource.remote.HomeRemoteDataSource
import com.hooshang.tmdb.feature.home.data.db.relation.crossref.PopularMovieGenreCrossRef
import com.hooshang.tmdb.feature.home.data.db.relation.crossref.TopMovieGenreCrossRef
import com.hooshang.tmdb.feature.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val localDataSource: HomeLocalDataSource,
    private val remoteDataSource: HomeRemoteDataSource
) : HomeRepository {
    override suspend fun getNowPlaying() = localDataSource.observeNowPlayingMovies()
        .map { movieFlow -> movieFlow.map { it.toDomainModel() } }

    override suspend fun getTopMovie() =
        localDataSource.observeTopMovies().map { movieFlow -> movieFlow.map { it.toDomainModel() } }

    override suspend fun getPopularMovie() = localDataSource.observePopularMovies()
        .map { movieFlow -> movieFlow.map { it.toDomainModel() } }

    override suspend fun fetchGenres() = localDataSource.insertGenres(
        remoteDataSource.getGenres().genres.map { it.toGenreEntity() }
    )


    override suspend fun fetchNowPlaying() = run {
        val data = remoteDataSource.getNowPlayingMovies().results

        localDataSource.observeNowPlayingMovies()
            .onEach { localMovies
                ->
                if (localMovies.map { it.movie?.id ?: -1 }
                        .compare(data.map { it.id })) {
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

    override suspend fun fetchTopMovie() = run {
        val data = remoteDataSource.getTopMovies().results

        localDataSource.observeTopMovies()
            .onEach { localMovies ->
                if (localMovies.map { it.movie?.id ?: -1 }
                        .compare(data.map { it.id })) {
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

    override suspend fun fetchPopularMovie() = run {
        val data = remoteDataSource.getMostPopularMovies().results

        localDataSource.observePopularMovies()
            .onEach { localMovies ->
                if (localMovies.map { it.movie?.id ?: -1 }.compare(data.map { it.id })) {
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

