package com.hooshang.tmdb.feature.detail.data.network.response

import kotlinx.serialization.Serializable

@Serializable
data class CastWithCrewResponse(
    val cast: List<CastOrCrewResponse>,
    val crew: List<CastOrCrewResponse>
)