package com.example.tmdb.feature.home.data.remote.json

import kotlinx.serialization.Serializable


@Serializable
data class MovieResponse(
    val results: List<MovieResult>,
)




