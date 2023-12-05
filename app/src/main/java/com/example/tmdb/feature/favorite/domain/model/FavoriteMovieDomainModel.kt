package com.example.tmdb.feature.favorite.domain.model

data class FavoriteMovieDomainModel(
    val id: Int,
    val title: String,
    val genres : String,
    val backdropPath : String,
    val voteAverage : Double
)
