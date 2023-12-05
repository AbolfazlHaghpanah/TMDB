package com.example.tmdb.feature.search.data.remote.remotedatasource

import com.example.tmdb.core.data.common.bodyOrThrow
import com.example.tmdb.feature.search.data.remote.SearchApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchRemoteDataSource @Inject constructor(
    private val searchApi: SearchApi
) {
    suspend fun searchMovie(query: String) = withContext(Dispatchers.IO) {
        bodyOrThrow { searchApi.getSearchResults(query = query) }
    }
}