package com.hooshang.tmdb.feature.detail.domain.model

data class SimilarMovieDomainModel(
    val id: Int,
    val genreIds: String,
    val title: String,
    val voteAverage: Float,
    val posterPath: String?
)
