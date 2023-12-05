package com.example.tmdb.feature.detail.data.source.remote.remotedatasource

import com.example.tmdb.core.data.source.remote.bodyOrThrow
import com.example.tmdb.feature.detail.data.model.remote.MovieDetailResponse
import com.example.tmdb.feature.detail.data.source.remote.api.DetailApi
import javax.inject.Inject

class DetailRemoteDataSource @Inject constructor(
    private val detailApi: DetailApi
) {
    suspend fun getMovieDetail(id: Int): MovieDetailResponse {
        return bodyOrThrow {
            detailApi.getMovieDetail(id = id)
        }
    }
}