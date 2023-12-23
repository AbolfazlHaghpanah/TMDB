package com.hooshang.tmdb.feature.detail.data.network.response

import kotlinx.serialization.Serializable

@Serializable
data class SimilarResponse(
    val results: List<SimilarMovieResultResponse>
)