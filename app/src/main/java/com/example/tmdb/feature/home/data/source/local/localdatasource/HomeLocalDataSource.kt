package com.example.tmdb.feature.home.data.source.local.localdatasource

import com.example.tmdb.core.data.model.local.GenreEntity
import com.example.tmdb.core.data.source.local.MovieDao
import com.example.tmdb.core.data.model.local.MovieEntity
import com.example.tmdb.feature.home.data.source.local.dao.HomeDao
import com.example.tmdb.feature.home.data.model.local.entity.NowPlayingEntity
import com.example.tmdb.feature.home.data.model.local.entity.PopularMovieEntity
import com.example.tmdb.feature.home.data.model.local.entity.TopMovieEntity
import com.example.tmdb.feature.home.data.model.local.relation.crossref.PopularMovieGenreCrossRef
import com.example.tmdb.feature.home.data.model.local.relation.crossref.TopMovieGenreCrossRef
import com.example.tmdb.feature.home.domain.model.HomeMovieDomainModel
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

    suspend fun storeGenres(
        genres: List<GenreEntity>
    ) {
        genres.forEach {
            homeDao.addGenre(it)
        }
    }

    suspend fun storePopularMovie(
        popularMovie: PopularMovieEntity,
        movie: MovieEntity,
        genres: List<Int>
    ) {
        homeDao.addPopularMovie(popularMovie)
        genres.forEach {
            homeDao.addPopularMoviesGenre(PopularMovieGenreCrossRef(movie.id, it))
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
