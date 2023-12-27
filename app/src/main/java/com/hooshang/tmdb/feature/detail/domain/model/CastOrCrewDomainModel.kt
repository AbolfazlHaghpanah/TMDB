package com.hooshang.tmdb.feature.detail.domain.model

data class CastOrCrewDomainModel(
    val id: Int,
    val name: String,
    val profilePath: String?,
    val job: String
)
