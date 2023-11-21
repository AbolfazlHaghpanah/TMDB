package com.example.tmdb.feature.detail.network.json

import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    val name: String,
    val id: Int
)