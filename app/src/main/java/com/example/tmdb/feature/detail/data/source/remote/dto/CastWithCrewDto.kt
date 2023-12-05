package com.example.tmdb.feature.detail.data.source.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CastWithCrewDto(
    val cast: List<CastOrCrewDto>,
    val crew: List<CastOrCrewDto>
)