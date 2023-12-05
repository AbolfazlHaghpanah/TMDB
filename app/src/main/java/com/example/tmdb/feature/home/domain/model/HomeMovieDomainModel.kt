package com.example.tmdb.feature.home.domain.model

data class HomeMovieDomainModel(
    val backdropPath: String = "",
    val releaseDate: String = "",
    val movieId: Int,
    val title: String,
    val posterPath: String,
    val voteAverage: Double,
    val genres: String = ""
)
