package com.example.tmdb.feature.home.data.popularMovie.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.tmdb.feature.home.data.popularMovie.entity.PopularMovieEntity

@Dao
interface PopularMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPopularMovie(movie: PopularMovieEntity)
}