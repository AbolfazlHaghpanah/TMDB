package com.hooshang.tmdb.feature.detail.data.model.remote

import kotlinx.serialization.Serializable

@Serializable
data class SimilarResultsResponse(
    val results: List<SimilarMovieResultResponse>
)