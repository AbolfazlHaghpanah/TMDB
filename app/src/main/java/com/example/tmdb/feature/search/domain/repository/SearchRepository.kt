package com.example.tmdb.feature.search.domain.repository

import com.example.tmdb.feature.search.domain.model.SearchMovieWithGenreDomainModel

interface SearchRepository {
    suspend fun searchMovie(value: String): List<SearchMovieWithGenreDomainModel>
}