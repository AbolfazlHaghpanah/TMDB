package com.hooshang.tmdb.feature.search.data.datasource.localdatasource

import com.hooshang.tmdb.feature.search.data.db.dao.SearchDao
import javax.inject.Inject

class SearchLocalDataSourceImpl @Inject constructor(
    private val searchDao: SearchDao
) : SearchLocalDataSource {
    override fun observeGenres() = searchDao.observeGenres()
}