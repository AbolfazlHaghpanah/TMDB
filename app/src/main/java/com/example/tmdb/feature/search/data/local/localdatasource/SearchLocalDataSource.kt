package com.example.tmdb.feature.search.data.local.localdatasource

import com.example.tmdb.feature.search.data.local.dao.SearchDao
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