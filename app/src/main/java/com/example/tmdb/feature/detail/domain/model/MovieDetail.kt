package com.example.tmdb.feature.detail.domain.model

data class MovieDetail(
    val id: Int,
    val title: String,
    val overview: String,
    val voteAverage: Float,
    val posterPath: String,
    val releaseDate: String,
    val runtime: Int,
    val genres: String,
    val externalIdsDto: ExternalIds?,
    val credits: List<CastOrCrew>,
    val similar: List<SimilarMovieResult>
)
