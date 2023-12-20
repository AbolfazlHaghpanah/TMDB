package com.hooshang.tmdb.feature.home.data.datasource.local

import com.hooshang.tmdb.core.data.model.local.GenreEntity
import com.hooshang.tmdb.core.data.model.local.MovieEntity
import com.hooshang.tmdb.feature.home.data.db.entity.NowPlayingEntity
import com.hooshang.tmdb.feature.home.data.db.entity.PopularMovieEntity
import com.hooshang.tmdb.feature.home.data.db.entity.TopMovieEntity
import com.hooshang.tmdb.feature.home.data.db.relation.crossref.PopularMovieGenreCrossRef
import com.hooshang.tmdb.feature.home.data.db.relation.crossref.TopMovieGenreCrossRef
import com.hooshang.tmdb.feature.home.domain.model.HomeMovieDomainModel
import kotlinx.coroutines.flow.Flow

interface HomeLocalDataSource {
    fun getNowPlayings(): Flow<List<HomeMovieDomainModel>>
    fun getTopMovie(): Flow<List<HomeMovieDomainModel>>
    fun getPopularMovies(): Flow<List<HomeMovieDomainModel>>
    suspend fun insertGenres(genres: List<GenreEntity>)
    suspend fun insertPopularMovies(popularMovie: List<PopularMovieEntity>)
    suspend fun insertPopularMoviesGenres(movies: List<PopularMovieGenreCrossRef>)
    suspend fun insertMovies(movie: List<MovieEntity>)
    suspend fun insertTopMovies(movies: List<TopMovieEntity>)
    suspend fun insertTopMoviesGenres(moviesAndGenres: List<TopMovieGenreCrossRef>)
    suspend fun insertNowPlayingMovies(nowPlaying: List<NowPlayingEntity>)
    fun removeNowPlayingMovies()
    fun removePopularMovies()
    fun removeTopMovies()
}