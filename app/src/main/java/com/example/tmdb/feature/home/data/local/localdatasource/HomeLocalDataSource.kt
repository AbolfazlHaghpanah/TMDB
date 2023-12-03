package com.example.tmdb.feature.home.data.local.localdatasource

import com.example.tmdb.core.data.genre.dao.GenreDao
import com.example.tmdb.core.data.genre.entity.GenreEntity
import com.example.tmdb.core.data.movie.dao.MovieDao
import com.example.tmdb.core.data.movie.entity.MovieEntity
import com.example.tmdb.feature.home.data.local.dao.HomeDao
import com.example.tmdb.feature.home.data.local.entity.NowPlayingEntity
import com.example.tmdb.feature.home.data.local.entity.PopularMovieEntity
import com.example.tmdb.feature.home.data.local.entity.TopMovieEntity
import com.example.tmdb.feature.home.data.local.relation.crossref.TopMovieGenreCrossRef
import com.example.tmdb.feature.home.ui.model.HomeMovieUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HomeLocalDataSource @Inject constructor(
    private val homeDao: HomeDao,
    private val movieDao: MovieDao,
    private val genreDao: GenreDao
) {
    fun getNowPlaying(): Flow<List<HomeMovieUiModel>> {
        return homeDao.observeNowPlayingMovie()
            .map { movieFlow -> movieFlow.map { it.toUiModel() } }
    }

    fun getTopMovie(): Flow<List<HomeMovieUiModel>> {
        return homeDao.observeTopMovie()
            .map { movieFlow -> movieFlow.map { it.toUiMovieModel() } }
    }

    fun getPopularMovie(): Flow<List<HomeMovieUiModel>> {
        return homeDao.observePopularMovie()
            .map { movieFlow -> movieFlow.map { it.toUiModel() } }
    }

    suspend fun storeGenres(
        genres: List<GenreEntity>
    ) {
        genres.forEach {
            genreDao.addGenre(it)
        }
    }

    suspend fun storePopularMovie(
        popularMovie: PopularMovieEntity,
        movie: MovieEntity,
        genres: List<Int>
    ) {
        homeDao.addPopularMovie(popularMovie)
        genres.forEach {
            homeDao.addTopMoviesGenre(TopMovieGenreCrossRef(movie.id, it))
        }
        movieDao.addMovie(movie)
    }

    suspend fun storeTopMovie(
        topMovie: TopMovieEntity,
        movie: MovieEntity,
        genres: List<Int>
    ) {
        homeDao.addTopMovie(topMovie)
        genres.forEach {
            homeDao.addTopMoviesGenre(TopMovieGenreCrossRef(movie.id, it))
        }
        movieDao.addMovie(movie)
    }

    suspend fun addNowPlaying(
        nowPlaying: NowPlayingEntity,
        movie: MovieEntity
    ) {
        homeDao.addNowPlayingMovie(nowPlaying)
        movieDao.addMovie(movie)
    }
}
