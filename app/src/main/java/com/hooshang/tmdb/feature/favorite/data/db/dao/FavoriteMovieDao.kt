package com.hooshang.tmdb.feature.favorite.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.hooshang.tmdb.feature.favorite.data.db.relation.FavoriteMovieWithMovieAndGenre
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMovieDao {

    @Query("SELECT * FROM favorite_movie")
    fun observeMovies(): Flow<List<FavoriteMovieWithMovieAndGenre>>

    @Query("DELETE FROM favorite_movie WHERE movieId = :id")
    fun removeMovie(id: Int)
}