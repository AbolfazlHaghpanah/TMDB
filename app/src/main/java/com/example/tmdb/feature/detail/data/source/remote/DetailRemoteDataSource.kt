package com.example.tmdb.feature.detail.data.source.remote

import com.example.tmdb.core.data.common.bodyOrThrow
import com.example.tmdb.feature.detail.data.source.remote.dto.MovieDetailDto
import javax.inject.Inject

class DetailRemoteDataSource @Inject constructor(
    private val detailApi: DetailApi
) {
    suspend fun getMovieDetail(id: Int): MovieDetailDto {
        return bodyOrThrow {
            detailApi.getMovieDetail(id = id)
        }
    }
}