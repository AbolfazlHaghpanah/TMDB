package com.hooshang.tmdb.feature.detail.data.datasource.remotedatasource

import com.hooshang.tmdb.feature.detail.data.network.response.MovieDetailResponse

interface DetailRemoteDataSource {
    suspend fun getMovieDetail(id: Int): MovieDetailResponse
}