package com.example.tmdb.feature.detail.data.source.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class GenreDto(
    val name: String,
    val id: Int
)