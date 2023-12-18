package com.hooshang.tmdb.feature.detail.data.model.remote

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class CastWithCrewResponse(
    val cast: List<CastOrCrewResponse>,
    val crew: List<CastOrCrewResponse>
)