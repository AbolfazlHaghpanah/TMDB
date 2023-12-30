package com.hooshang.tmdb.feature.search.domain.use_case

import com.hooshang.tmdb.feature.search.domain.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val searchRepository: SearchRepository,
    private val dispatcher: Dispatchers
) {
    suspend operator fun invoke(query: String) = withContext(dispatcher.IO) {
        searchRepository.searchMovie(query)
    }
}