package com.hooshang.tmdb.feature.search.data.source.remote.remotedatasource

import com.hooshang.tmdb.core.data.source.remote.bodyOrThrow
import com.hooshang.tmdb.feature.search.data.source.remote.api.SearchApi
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