package com.hooshang.tmdb.feature.home.data.datasource.local

import com.hooshang.tmdb.core.data.model.local.GenreEntity
import com.hooshang.tmdb.core.data.model.local.MovieEntity
import com.hooshang.tmdb.core.data.source.local.MovieDao
import com.hooshang.tmdb.feature.home.data.db.HomeDao
import com.hooshang.tmdb.feature.home.data.db.entity.NowPlayingEntity
import com.hooshang.tmdb.feature.home.data.db.entity.PopularMovieEntity
import com.hooshang.tmdb.feature.home.data.db.entity.TopMovieEntity
import com.hooshang.tmdb.feature.home.data.db.relation.crossref.PopularMovieGenreCrossRef
import com.hooshang.tmdb.feature.home.data.db.relation.crossref.TopMovieGenreCrossRef
import com.hooshang.tmdb.feature.home.domain.model.HomeMovieDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HomeLocalDataSourceImpl @Inject constructor(
    private val homeDao: HomeDao,
    private val movieDao: MovieDao,
) : HomeLocalDataSource {
    override fun getNowPlayings(): Flow<List<HomeMovieDomainModel>> {
        return homeDao.observeNowPlayingMovies()
            .map { movieFlow -> movieFlow.map { it.toDomainModel() } }
    }

    override fun getTopMovie(): Flow<List<HomeMovieDomainModel>> {
        return homeDao.observeTopMovies()
            .map { movieFlow -> movieFlow.map { it.toDomainModel() } }
    }

    override fun getPopularMovies(): Flow<List<HomeMovieDomainModel>> {
        return homeDao.observePopularMovies()
            .map { movieFlow -> movieFlow.map { it.toDomainModel() } }
    }

    override suspend fun insertGenres(
        genres: List<GenreEntity>
    ) {
        homeDao.addGenres(genres)
    }

    override suspend fun insertPopularMovies(
        popularMovie: List<PopularMovieEntity>
    ) {
        homeDao.addPopularMovies(popularMovie)
    }

    override suspend fun insertPopularMoviesGenres(
        movies: List<PopularMovieGenreCrossRef>
    ) {
        homeDao.addPopularMoviesGenre(movies)
    }

    override suspend fun insertMovies(
        movie: List<MovieEntity>
    ) {
        movieDao.insertMovies(movie)
    }

    override suspend fun insertTopMovies(
        movies: List<TopMovieEntity>
    ) {
        homeDao.addTopMovies(movies)
    }

    override suspend fun insertTopMoviesGenres(
        moviesAndGenres: List<TopMovieGenreCrossRef>
    ) {
        homeDao.addTopMoviesGenre(moviesAndGenres)
    }

    override suspend fun insertNowPlayingMovies(
        nowPlaying: List<NowPlayingEntity>
    ) {
        homeDao.addNowPlayingMovies(nowPlaying)
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
