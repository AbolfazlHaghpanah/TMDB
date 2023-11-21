package com.example.tmdb.feature.detail.network.json

import kotlinx.serialization.Serializable

@Serializable
data class SimilarResults(
    val results: List<SimilarMovieResult>
)