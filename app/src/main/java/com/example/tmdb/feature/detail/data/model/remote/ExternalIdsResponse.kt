package com.example.tmdb.feature.detail.data.model.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExternalIdsResponse(
    @SerialName("imdb_id")
    val imdbId: String?,
    @SerialName("instagram_id")
    val instagramId: String?,
    @SerialName("twitter_id")
    val twitterId: String?
)