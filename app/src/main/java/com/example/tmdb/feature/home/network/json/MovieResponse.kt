package com.example.tmdb.feature.home.network.json

import kotlinx.serialization.Serializable


@Serializable
data class MovieResponse(
    val results: List<MovieResult>
)




