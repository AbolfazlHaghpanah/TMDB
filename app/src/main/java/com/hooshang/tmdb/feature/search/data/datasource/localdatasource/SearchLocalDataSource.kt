package com.hooshang.tmdb.feature.search.data.datasource.localdatasource

import com.hooshang.tmdb.core.data.db.entity.GenreEntity
import kotlinx.coroutines.flow.Flow

interface SearchLocalDataSource {
    fun observeGenres(): Flow<List<GenreEntity>>
}