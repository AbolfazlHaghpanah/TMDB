package com.example.tmdb.core.data.moviedata

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.tmdb.feature.home.data.topmovie.TopMovieEntity
import com.example.tmdb.feature.home.data.topmovie.relation.TopMovieAndGenreWithMovie
import com.example.tmdb.feature.home.data.topmovie.relation.crossref.TopMovieGenreCrossRef
import com.example.tmdb.feature.home.data.nowplayingmovie.NowPlayingEntity
import com.example.tmdb.feature.home.data.nowplayingmovie.relation.NowPlayingWithMovie
import com.example.tmdb.feature.home.data.popularMovie.PopularMovieEntity
import com.example.tmdb.feature.home.data.popularMovie.relation.PopularMovieAndGenreWithMovie
import com.example.tmdb.feature.home.data.popularMovie.relation.PopularMovieGenreCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * From Movie")
    fun observeMovie(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovie(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNowPlayingMovie(movie: NowPlayingEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPopularMovie(movie: PopularMovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTopMovie(movie: TopMovieEntity)

    @Transaction
    @Query("SELECT * FROM POPULAR_MOVIES")
    fun observePopularMovie(): Flow<List<PopularMovieAndGenreWithMovie>>

    @Transaction
    @Query("SELECT * FROM TOP_MOVIE")
    fun observeTopMovie(): Flow<List<TopMovieAndGenreWithMovie>>

    @Transaction
    @Query("SELECT * FROM NOW_PLAYING")
    fun observeNowPlayingMovie(): Flow<List<NowPlayingWithMovie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTopMoviesGenre(genre: TopMovieGenreCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPopularMoviesGenre(genre: PopularMovieGenreCrossRef)
}