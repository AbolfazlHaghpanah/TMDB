package com.example.tmdb.feature.home.data.topmovies

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.tmdb.feature.home.data.popularmovies.PopularMovieEntity

@Dao
interface TopMovieDao{

    @Query("SELECT * FROM TOP_MOVIE")
    fun observeMovies(): List<TopMovieEntity>

    @Insert
    fun addMovie(movie : TopMovieEntity)
}