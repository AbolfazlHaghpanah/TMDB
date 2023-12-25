package com.hooshang.tmdb.feature.detail.data.network.response

import kotlinx.serialization.Serializable

@Serializable
data class GenreResponse(
    val name: String,
    val id: Int
)