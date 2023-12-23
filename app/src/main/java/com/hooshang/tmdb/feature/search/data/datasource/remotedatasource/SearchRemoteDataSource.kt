package com.hooshang.tmdb.feature.search.data.datasource.remotedatasource

import com.hooshang.tmdb.feature.search.data.network.response.SearchResponse

interface SearchRemoteDataSource {
    suspend fun searchMovie(query: String): SearchResponse
}