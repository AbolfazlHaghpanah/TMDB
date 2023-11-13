package com.example.tmdb.feature.home.network.json

import kotlinx.serialization.Serializable


@Serializable
data class NowPlayingResponse(
    val results: List<MovieResponse>
)




