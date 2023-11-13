package com.example.tmdb.feature.home.data.popularmovies

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.tmdb.feature.home.network.json.MovieResponse

@Dao
interface PopularMovieDao {

    @Query("SELECT * FROM POPULAR_MOVIES")
    fun observeMovies(): List<PopularMovieEntity>

    @Insert
    fun addMovie(movie : PopularMovieEntity)
}