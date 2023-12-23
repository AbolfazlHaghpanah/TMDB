package com.hooshang.tmdb.feature.search.data.network.api

import com.hooshang.tmdb.core.utils.tmdpApiKey
import com.hooshang.tmdb.feature.search.data.network.response.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface SearchApi {
    @GET("search/movie?language=en-US&page=1")
    suspend fun getSearchResults(
        @Header("Authorization") apiKey: String = tmdpApiKey,
        @Query("query") query: String
    ): Response<SearchResponse>
}