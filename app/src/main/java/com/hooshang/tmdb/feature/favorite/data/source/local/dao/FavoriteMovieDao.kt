package com.hooshang.tmdb.feature.favorite.data.source.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.hooshang.tmdb.feature.favorite.data.model.local.relation.FavoriteMovieWithMovieAndGenre
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMovieDao {

    @Query("SELECT * FROM FAVORITE_MOVIE")
    fun observeMovies(): Flow<List<FavoriteMovieWithMovieAndGenre>>

    @Query("DELETE FROM FAVORITE_MOVIE WHERE movieId = :id")
    fun deleteMovie(id: Int)
}