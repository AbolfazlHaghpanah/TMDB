package com.example.tmdb.feature.search.network

import com.example.tmdb.core.utils.tmdpApiKey
import com.example.tmdb.feature.search.network.json.SearchResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface SearchApi {
    @GET("search/movie?language=en-US&page=1")
    suspend fun getSearchResults(
        @Header("Authorization") apiKey: String = tmdpApiKey,
        @Query("query") query: String
    ): Response<SearchResult>
}