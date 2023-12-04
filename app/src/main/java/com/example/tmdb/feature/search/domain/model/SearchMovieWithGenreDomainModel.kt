package com.example.tmdb.feature.search.domain.model

data class SearchMovieWithGenreDomainModel (
    val movieDomainModel: SearchMovieDomainModel,
    val genres : String
)