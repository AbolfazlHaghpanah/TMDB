package com.example.tmdb.feature.search.data.source.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.tmdb.core.data.model.local.GenreEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchDao {
    @Query("SELECT * FROM genres")
    fun observeGenres(): Flow<List<GenreEntity>>
}