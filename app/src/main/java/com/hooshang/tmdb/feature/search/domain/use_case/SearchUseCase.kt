package com.hooshang.tmdb.feature.search.domain.use_case

import com.hooshang.tmdb.feature.search.domain.repository.SearchRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val searchRepository: SearchRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(query: String) = withContext(coroutineDispatcher) {
        searchRepository.searchMovie(query)
    }
}