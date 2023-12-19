package com.hooshang.tmdb.feature.search.data.source.remote.api

import com.hooshang.tmdb.feature.search.data.model.remote.SearchResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface SearchApi {
    @GET("search/movie?language=en-US&page=1")
    suspend fun getSearchResults(
        @Query("query") query: String
    ): Response<SearchResult>
}