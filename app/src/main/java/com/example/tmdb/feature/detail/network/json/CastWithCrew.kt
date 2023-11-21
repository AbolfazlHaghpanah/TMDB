package com.example.tmdb.feature.detail.network.json

import kotlinx.serialization.Serializable

@Serializable
data class CastWithCrew(
    val cast: List<CastOrCrew>,
    val crew: List<CastOrCrew>
)