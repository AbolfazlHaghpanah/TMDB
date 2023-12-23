package com.hooshang.tmdb.feature.search.data.datasource.localdatasource

import com.hooshang.tmdb.feature.search.data.db.dao.SearchDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchLocalDataSource @Inject constructor(
    private val searchDao: SearchDao
) {
    suspend fun getGenres() = withContext(Dispatchers.IO) {
        searchDao.observeGenres()
    }
}