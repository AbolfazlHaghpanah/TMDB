package com.hooshang.tmdb.core.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.hooshang.tmdb.core.data.model.local.MovieEntity

@Dao
interface MovieDao {

    //    TODO rename for example => addMovies
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovie(movie: List<MovieEntity>)
}