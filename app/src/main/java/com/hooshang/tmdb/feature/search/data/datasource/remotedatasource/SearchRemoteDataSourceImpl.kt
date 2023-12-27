package com.hooshang.tmdb.feature.search.data.datasource.remotedatasource

import com.hooshang.tmdb.core.data.source.remote.bodyOrThrow
import com.hooshang.tmdb.feature.search.data.network.api.SearchApi
import javax.inject.Inject

class SearchRemoteDataSourceImpl @Inject constructor(
    private val searchApi: SearchApi
) : SearchRemoteDataSource {
    override suspend fun searchMovie(query: String) = bodyOrThrow {
        searchApi.getSearchResults(query = query)
    }
}