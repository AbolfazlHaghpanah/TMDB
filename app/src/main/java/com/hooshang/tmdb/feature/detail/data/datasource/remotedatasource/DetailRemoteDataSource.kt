package com.hooshang.tmdb.feature.detail.data.datasource.remotedatasource

import com.hooshang.tmdb.core.data.source.remote.bodyOrThrow
import com.hooshang.tmdb.feature.detail.data.network.response.MovieDetailResponse
import com.hooshang.tmdb.feature.detail.data.network.DetailApi
import javax.inject.Inject

class DetailRemoteDataSource @Inject constructor(
    private val detailApi: DetailApi
) {
    suspend fun getMovieDetail(id: Int): MovieDetailResponse = bodyOrThrow {
        detailApi.getMovieDetail(id = id)
    }
}