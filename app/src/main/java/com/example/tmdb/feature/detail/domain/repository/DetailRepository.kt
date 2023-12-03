package com.example.tmdb.feature.detail.domain.repository

interface DetailRepository {
    fun addToFavorite(id: Int)

    fun removeFromFavorite(id: Int)

    fun observeDetailMovieWithAllRelations()

    fun fetchMovieDetail()

    fun addMovieDetail()

}