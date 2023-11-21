package com.example.tmdb.feature.detail.network.json

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SimilarMovieResult(

    val id: Int,
    @SerialName("genre_ids")
    val genreIds: List<Int>,

    @SerialName("original_title")
    val originalTitle: String,

    @SerialName("vote_average")
    val voteAverage: Float,

    @SerialName("poster_path")
    val posterPath: String?
)