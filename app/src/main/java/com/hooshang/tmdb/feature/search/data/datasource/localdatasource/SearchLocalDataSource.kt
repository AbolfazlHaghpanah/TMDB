package com.hooshang.tmdb.feature.search.data.datasource.localdatasource

import com.hooshang.tmdb.core.data.model.local.GenreEntity
import kotlinx.coroutines.flow.Flow

interface SearchLocalDataSource {
    fun getGenres(): Flow<List<GenreEntity>>
}