package com.example.tmdb.feature.detail.data.source.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class SimilarResultsDto(
    val results: List<SimilarMovieResultDto>
)