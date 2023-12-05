package com.example.tmdb.feature.home.data.model.remote

import kotlinx.serialization.Serializable


@Serializable
data class MovieResponse(
    val results: List<MovieResult>,
)




