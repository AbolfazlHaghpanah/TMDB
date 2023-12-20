package com.hooshang.tmdb.feature.home.data.network.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable


@Keep
@Serializable
data class MovieResponse(
    val results: List<MovieResult>,
)




