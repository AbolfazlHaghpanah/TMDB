package com.hooshang.tmdb.feature.search.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.hooshang.tmdb.core.data.model.local.GenreEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchDao {
    @Query("SELECT * FROM genres")
    fun observeGenres(): Flow<List<GenreEntity>>
}