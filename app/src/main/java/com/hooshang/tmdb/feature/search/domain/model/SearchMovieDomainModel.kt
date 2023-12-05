package com.hooshang.tmdb.feature.search.domain.model

data class SearchMovieDomainModel(
    val id: Int,
    val title: String,
    val posterPath: String,
    val voteAverage: Float,
    val backdropPath: String,
    val releaseDate : String ,
    val originalLanguage : String
)