package com.hooshang.tmdb.feature.search.data.network.api

import com.hooshang.tmdb.feature.search.data.network.response.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface SearchApi {
    @GET("search/movie?language=en-US&page=1")
    suspend fun getSearchResults(
        @Query("query") query: String
    ): Response<SearchResponse>
}