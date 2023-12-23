package com.hooshang.tmdb.feature.search.domain.use_case

import com.hooshang.tmdb.feature.search.domain.model.SearchMovieWithGenreDomainModel
import com.hooshang.tmdb.feature.search.domain.repository.SearchRepository
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(query: String): List<SearchMovieWithGenreDomainModel> {
        return searchRepository.searchMovie(query)
    }
}