package com.example.tmdb.feature.home.data.nowplaying

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.tmdb.feature.home.data.popularmovies.PopularMovieEntity

@Dao
interface NowPlayingDao {

    @Query("SELECT * FROM NOW_PLAYING")
    fun observeMovies(): List<NowPlayingEntity>

    @Insert
    fun addMovie(movie : NowPlayingEntity)
}