package com.hooshang.tmdb.feature.home.data.source.local.localdatasource

import com.hooshang.tmdb.core.data.model.local.GenreEntity
import com.hooshang.tmdb.core.data.model.local.MovieEntity
import com.hooshang.tmdb.core.data.source.local.MovieDao
import com.hooshang.tmdb.feature.home.data.model.local.entity.NowPlayingEntity
import com.hooshang.tmdb.feature.home.data.model.local.entity.PopularMovieEntity
import com.hooshang.tmdb.feature.home.data.model.local.entity.TopMovieEntity
import com.hooshang.tmdb.feature.home.data.model.local.relation.crossref.PopularMovieGenreCrossRef
import com.hooshang.tmdb.feature.home.data.model.local.relation.crossref.TopMovieGenreCrossRef
import com.hooshang.tmdb.feature.home.data.source.local.dao.HomeDao
import com.hooshang.tmdb.feature.home.domain.model.HomeMovieDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HomeLocalDataSource @Inject constructor(
    private val homeDao: HomeDao,
    private val movieDao: MovieDao,
) {
    fun getNowPlayings(): Flow<List<HomeMovieDomainModel>> {
        return homeDao.observeNowPlayingMovies()
            .map { movieFlow -> movieFlow.map { it.toDomainModel() } }
    }

    fun getTopMovie(): Flow<List<HomeMovieDomainModel>> {
        return homeDao.observeTopMovies()
            .map { movieFlow -> movieFlow.map { it.toDomainModel() } }
    }

    fun getPopularMovies(): Flow<List<HomeMovieDomainModel>> {
        return homeDao.observePopularMovies()
            .map { movieFlow -> movieFlow.map { it.toDomainModel() } }
    }

    suspend fun insertGenres(
        genres: List<GenreEntity>
    ) {
        homeDao.addGenres(genres)
    }

    suspend fun insertPopularMovies(
        popularMovie: List<PopularMovieEntity>
    ) {
        homeDao.addPopularMovies(popularMovie)
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
        homeDao.addTopMovies(movies)
    }

    suspend fun insertTopMoviesGenres(
        moviesAndGenres: List<TopMovieGenreCrossRef>
    ) {
        homeDao.addTopMoviesGenre(moviesAndGenres)
    }

    suspend fun insertNowPlayingMovies(
        nowPlaying: List<NowPlayingEntity>
    ) {
        homeDao.addNowPlayingMovies(nowPlaying)
    }

    fun removeNowPlayingMovies(
    ) {
        homeDao.removeNowPlayingMovies()
    }

    fun removePopularMovies(
    ) {
        homeDao.removePopularMovies()
        homeDao.removePopularMoviesGenre()
    }

    fun removeTopMovies(
    ) {
        homeDao.removeTopMovies()
        homeDao.removeTopMoviesGenre()
    }
}
