package com.hooshang.tmdb.feature.home.data.model.remote

import androidx.annotation.Keep
import kotlinx.serialization.Serializable


@Keep
@Serializable
data class MovieResponse(
    val results: List<MovieResult>,
)




