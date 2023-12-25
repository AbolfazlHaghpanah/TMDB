package com.hooshang.tmdb.feature.detail.data.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SimilarMovieResultResponse(
    val id: Int,
    @SerialName("genre_ids")
    val genreIds: List<Int>,
    val title: String,
    @SerialName("vote_average")
    val voteAverage: Float,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("backdrop_path")
    val backdropPath: String?,
)