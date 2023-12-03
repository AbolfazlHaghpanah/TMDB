package com.example.tmdb.feature.detail.domain.model

data class CastOrCrew(
    val id: Int,
    val name: String,
    val profilePath: String?,
    val job: String? = null
)
