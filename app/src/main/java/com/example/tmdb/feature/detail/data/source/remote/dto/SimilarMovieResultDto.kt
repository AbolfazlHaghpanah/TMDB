package com.example.tmdb.feature.detail.data.source.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SimilarMovieResultDto(
    val id: Int,
    @SerialName("genre_ids")
    val genreIds: List<Int>,
    val title: String,
    @SerialName("vote_average")
    val voteAverage: Float,
    @SerialName("poster_path")
    val posterPath: String?
)