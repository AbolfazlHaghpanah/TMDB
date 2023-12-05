package com.example.tmdb.feature.detail.domain.model

data class SimilarMovie(
    val id: Int,
    val genreIds: String,
    val title: String,
    val voteAverage: Float,
    val posterPath: String?
)
