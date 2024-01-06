package com.hooshang.tmdb.feature.home.data.datasource.local

import com.hooshang.tmdb.core.data.db.entity.GenreEntity
import com.hooshang.tmdb.core.data.db.entity.MovieEntity
import com.hooshang.tmdb.feature.home.data.db.entity.NowPlayingEntity
import com.hooshang.tmdb.feature.home.data.db.entity.PopularMovieEntity
import com.hooshang.tmdb.feature.home.data.db.entity.TopMovieEntity
import com.hooshang.tmdb.feature.home.data.db.relation.NowPlayingWithMovie
import com.hooshang.tmdb.feature.home.data.db.relation.PopularMovieAndGenreWithMovie
import com.hooshang.tmdb.feature.home.data.db.relation.TopMovieAndGenreWithMovie
import com.hooshang.tmdb.feature.home.data.db.relation.crossref.PopularMovieGenreCrossRef
import com.hooshang.tmdb.feature.home.data.db.relation.crossref.TopMovieGenreCrossRef
import kotlinx.coroutines.flow.Flow

interface HomeLocalDataSource {
    fun observeNowPlayingMovies(): Flow<List<NowPlayingWithMovie>>
    fun observeTopMovies(): Flow<List<TopMovieAndGenreWithMovie>>
    fun observePopularMovies(): Flow<List<PopularMovieAndGenreWithMovie>>
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