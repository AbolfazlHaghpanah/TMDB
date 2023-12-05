package com.hooshang.tmdb.feature.search.domain.use_case

import com.hooshang.tmdb.feature.search.domain.model.SearchMovieWithGenreDomainModel
import com.hooshang.tmdb.feature.search.domain.repository.SearchRepository

class SearchUseCase(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(value: String): List<SearchMovieWithGenreDomainModel> {
        return searchRepository.searchMovie(value)
    }
}
