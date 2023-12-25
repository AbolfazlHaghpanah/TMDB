package com.hooshang.tmdb.feature.search.data.datasource.localdatasource

import com.hooshang.tmdb.core.data.model.local.GenreEntity
import kotlinx.coroutines.flow.Flow

interface SearchLocalDataSource {
//    TODO rename observeGenres seems to be better
    fun getGenres(): Flow<List<GenreEntity>>
}