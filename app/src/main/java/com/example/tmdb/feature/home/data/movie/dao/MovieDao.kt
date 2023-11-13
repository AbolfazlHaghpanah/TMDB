package com.example.tmdb.feature.home.data.movie.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tmdb.feature.home.data.movie.entity.NowPlayingEntity
import com.example.tmdb.feature.home.data.movie.entity.PopularMovieEntity
import com.example.tmdb.feature.home.data.movie.entity.TopMovieEntity
import com.example.tmdb.feature.home.data.relation.PopularMovieWithGenre
import com.example.tmdb.feature.home.data.relation.TopMovieWithGenre
import com.example.tmdb.feature.home.data.relation.crossref.PopularMovieGenreCrossRef
import com.example.tmdb.feature.home.data.relation.crossref.TopMovieGenreCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao{

    @Query("SELECT * FROM NOW_PLAYING")
    fun observeNowPlayingMovies(): Flow<List<NowPlayingEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNowPlayingMovie(movie : NowPlayingEntity)

    @Query("SELECT * FROM POPULAR_MOVIES")
    fun observePopularMovies(): Flow<List<PopularMovieEntity>>

    @Query("SELECT * FROM POPULAR_MOVIES")
    fun observePopularMoviesWithGenre(): Flow<List<PopularMovieWithGenre>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPopularMoviesGenre(genre : PopularMovieGenreCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPopularMovie(movie : PopularMovieEntity)

    @Query("SELECT * FROM TOP_MOVIE")
    fun observeTopMovies(): Flow<List<TopMovieEntity>>

    @Query("SELECT * FROM TOP_MOVIE")
    fun observeTopMovieWithGenre(): Flow<List<TopMovieWithGenre>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTopMoviesGenre(genre : TopMovieGenreCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTopMovie(movie : TopMovieEntity)

}