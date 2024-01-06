package com.hooshang.tmdb.feature.detail.data.datasource.remotedatasource

import com.hooshang.tmdb.core.data.network.common.bodyOrThrow
import com.hooshang.tmdb.feature.detail.data.network.api.DetailApi
import com.hooshang.tmdb.feature.detail.data.network.response.MovieDetailResponse
import javax.inject.Inject

class DetailRemoteDataSourceImpl @Inject constructor(
    private val detailApi: DetailApi
) : DetailRemoteDataSource {
    override suspend fun getMovieDetail(id: Int): MovieDetailResponse = bodyOrThrow {
        detailApi.getMovieDetail(id = id)
    }
}