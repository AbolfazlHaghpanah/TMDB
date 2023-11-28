package com.example.tmdb.feature.home.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tmdb.feature.home.data.entity.NowPlayingEntity
import com.example.tmdb.feature.home.data.relation.NowPlayingWithMovie
import com.example.tmdb.feature.home.data.entity.PopularMovieEntity
import com.example.tmdb.feature.home.data.relation.PopularMovieAndGenreWithMovie
import com.example.tmdb.feature.home.data.relation.crossref.PopularMovieGenreCrossRef
import com.example.tmdb.feature.home.data.entity.TopMovieEntity
import com.example.tmdb.feature.home.data.relation.TopMovieAndGenreWithMovie
import com.example.tmdb.feature.home.data.relation.crossref.TopMovieGenreCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface HomeDao {

    //    cross references
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTopMoviesGenre(genre: TopMovieGenreCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPopularMoviesGenre(genre: PopularMovieGenreCrossRef)

    @Query("SELECT * FROM POPULAR_MOVIES")
    fun observePopularMovie(): Flow<List<PopularMovieAndGenreWithMovie>>

    @Query("SELECT * FROM TOP_MOVIE")
    fun observeTopMovie(): Flow<List<TopMovieAndGenreWithMovie>>

    @Query("SELECT * FROM NOW_PLAYING")
    fun observeNowPlayingMovie(): Flow<List<NowPlayingWithMovie>>

    //    other entities
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNowPlayingMovie(movie: NowPlayingEntity)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPopularMovie(movie: PopularMovieEntity)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTopMovie(movie: TopMovieEntity)

}