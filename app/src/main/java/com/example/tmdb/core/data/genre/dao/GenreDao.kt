package com.example.tmdb.core.data.genre.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tmdb.core.data.genre.entity.GenreEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GenreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGenre(genre: GenreEntity)

    @Query("SELECT * FROM genres")
    fun observeGenres(): Flow<List<GenreEntity>>
}