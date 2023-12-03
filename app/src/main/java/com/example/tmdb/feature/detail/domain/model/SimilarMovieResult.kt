package com.example.tmdb.feature.detail.domain.model

data class SimilarMovieResult(
    val id: Int,
    val genreIds: List<Int>,
    val title: String,
    val voteAverage: Float,
    val posterPath: String?
)
