package com.example.tmdb.feature.favorite.data

import androidx.room.Dao
import androidx.room.Query
import com.example.tmdb.feature.favorite.data.relation.FavoriteMovieWithMovieAndGenre
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMovieDao {
    @Query("SELECT * FROM FAVORITE_MOVIE")
    fun observeMovies(): Flow<List<FavoriteMovieWithMovieAndGenre>>

    @Query("DELETE FROM FAVORITE_MOVIE WHERE movieId = :id")
    fun deleteMovie(id: Int)
}