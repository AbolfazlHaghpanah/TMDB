package com.hooshang.tmdb.feature.home.data.datasource.local

import com.hooshang.tmdb.core.data.model.local.GenreEntity
import com.hooshang.tmdb.core.data.model.local.MovieEntity
import com.hooshang.tmdb.core.data.source.local.MovieDao
import com.hooshang.tmdb.feature.home.data.db.dao.HomeDao
import com.hooshang.tmdb.feature.home.data.db.entity.NowPlayingEntity
import com.hooshang.tmdb.feature.home.data.db.entity.PopularMovieEntity
import com.hooshang.tmdb.feature.home.data.db.entity.TopMovieEntity
import com.hooshang.tmdb.feature.home.data.db.relation.NowPlayingWithMovie
import com.hooshang.tmdb.feature.home.data.db.relation.PopularMovieAndGenreWithMovie
import com.hooshang.tmdb.feature.home.data.db.relation.TopMovieAndGenreWithMovie
import com.hooshang.tmdb.feature.home.data.db.relation.crossref.PopularMovieGenreCrossRef
import com.hooshang.tmdb.feature.home.data.db.relation.crossref.TopMovieGenreCrossRef
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeLocalDataSourceImpl @Inject constructor(
    private val homeDao: HomeDao,
    private val movieDao: MovieDao,
) : HomeLocalDataSource {
    override fun observeNowPlayingMovies(): Flow<List<NowPlayingWithMovie>> =
        homeDao.observeNowPlayingMovies()

    override fun observeTopMovies(): Flow<List<TopMovieAndGenreWithMovie>> =
        homeDao.observeTopMovies()

    override fun observePopularMovies(): Flow<List<PopularMovieAndGenreWithMovie>> =
        homeDao.observePopularMovies()

    override suspend fun insertGenres(
        genres: List<GenreEntity>
    ) {
        homeDao.insertGenres(genres)
    }

    override suspend fun insertPopularMovies(
        popularMovie: List<PopularMovieEntity>
    ) {
        homeDao.insertPopularMovies(popularMovie)
    }

    override suspend fun insertPopularMoviesGenres(
        movies: List<PopularMovieGenreCrossRef>
    ) {
        homeDao.insertPopularMoviesGenre(movies)
    }

    override suspend fun insertMovies(
        movie: List<MovieEntity>
    ) {
        movieDao.insertMovies(movie)
    }

    override suspend fun insertTopMovies(
        movies: List<TopMovieEntity>
    ) {
        homeDao.insertTopMovies(movies)
    }

    override suspend fun insertTopMoviesGenres(
        moviesAndGenres: List<TopMovieGenreCrossRef>
    ) {
        homeDao.insertTopMoviesGenre(moviesAndGenres)
    }

    override suspend fun insertNowPlayingMovies(
        nowPlaying: List<NowPlayingEntity>
    ) {
        homeDao.insertNowPlayingMovies(nowPlaying)
    }

    override fun removeNowPlayingMovies() {
        homeDao.removeNowPlayingMovies()
    }

    override fun removePopularMovies() {
        homeDao.removePopularMovies()
        homeDao.removePopularMoviesGenre()
    }

    override fun removeTopMovies() {
        homeDao.removeTopMovies()
        homeDao.removeTopMoviesGenre()
    }
}
