package com.hooshang.tmdb.feature.detail.data.model.remote

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class GenreResponse(
    val name: String,
    val id: Int
)