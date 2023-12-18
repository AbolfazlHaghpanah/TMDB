package com.hooshang.tmdb.feature.detail.data.model.remote

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
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