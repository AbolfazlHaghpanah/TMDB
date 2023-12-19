package com.hooshang.tmdb.feature.home.data.source.local.localdatasource

import com.hooshang.tmdb.core.data.model.local.GenreEntity
import com.hooshang.tmdb.core.data.source.local.MovieDao
import com.hooshang.tmdb.core.data.model.local.MovieEntity
import com.hooshang.tmdb.feature.home.data.source.local.dao.HomeDao
import com.hooshang.tmdb.feature.home.data.model.local.entity.NowPlayingEntity
import com.hooshang.tmdb.feature.home.data.model.local.entity.PopularMovieEntity
import com.hooshang.tmdb.feature.home.data.model.local.entity.TopMovieEntity
import com.hooshang.tmdb.feature.home.data.model.local.relation.crossref.PopularMovieGenreCrossRef
import com.hooshang.tmdb.feature.home.data.model.local.relation.crossref.TopMovieGenreCrossRef
import com.hooshang.tmdb.feature.home.domain.model.HomeMovieDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HomeLocalDataSource @Inject constructor(
    private val homeDao: HomeDao,
    private val movieDao: MovieDao,
) {
    fun getNowPlaying(): Flow<List<HomeMovieDomainModel>> {
        return homeDao.observeNowPlayingMovie()
            .map { movieFlow -> movieFlow.map { it.toDomainModel() } }
    }

    fun getTopMovie(): Flow<List<HomeMovieDomainModel>> {
        return homeDao.observeTopMovie()
            .map { movieFlow -> movieFlow.map { it.toDomainModel() } }
    }

    fun getPopularMovie(): Flow<List<HomeMovieDomainModel>> {
        return homeDao.observePopularMovie()
            .map { movieFlow -> movieFlow.map { it.toDomainModel() } }
    }

    suspend fun insertGenres(
        genres: List<GenreEntity>
    ) {
        homeDao.addGenre(genres)
    }

    suspend fun insertPopularMovie(
        popularMovie: List<PopularMovieEntity>
    ) {
        homeDao.addPopularMovie(popularMovie)
    }

    suspend fun insertPopularMoviesGenres(
        movies: List<PopularMovieGenreCrossRef>
    ) {
        homeDao.addPopularMoviesGenre(movies)
    }

    suspend fun insertMovies(
        movie: List<MovieEntity>
    ) {
        movieDao.insertMovies(movie)
    }

    suspend fun insertTopMovies(
        movies: List<TopMovieEntity>
    ) {
        homeDao.addTopMovie(movies)
    }

    suspend fun insertTopMoviesGenres(
        moviesAndGenres: List<TopMovieGenreCrossRef>
    ) {
        homeDao.addTopMoviesGenre(moviesAndGenres)
    }

    suspend fun insertNowPlayingMovie(
        nowPlaying: List<NowPlayingEntity>
    ) {
        homeDao.addNowPlayingMovie(nowPlaying)
    }
}
