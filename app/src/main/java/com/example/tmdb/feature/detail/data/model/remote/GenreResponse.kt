package com.example.tmdb.feature.detail.data.model.remote

import kotlinx.serialization.Serializable

@Serializable
data class GenreResponse(
    val name: String,
    val id: Int
)