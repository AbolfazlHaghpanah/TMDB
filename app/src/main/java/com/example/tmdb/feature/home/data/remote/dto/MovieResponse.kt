package com.example.tmdb.feature.home.data.remote.dto

import kotlinx.serialization.Serializable


@Serializable
data class MovieResponse(
    val results: List<MovieResult>,
)




