package com.hooshang.tmdb.feature.search.domain.repository

import com.hooshang.tmdb.feature.search.domain.model.SearchMovieWithGenreDomainModel

interface SearchRepository {
    suspend fun searchMovie(value: String): List<SearchMovieWithGenreDomainModel>
}