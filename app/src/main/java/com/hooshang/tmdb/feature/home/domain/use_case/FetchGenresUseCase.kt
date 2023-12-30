package com.hooshang.tmdb.feature.home.domain.use_case

import com.hooshang.tmdb.feature.home.domain.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchGenresUseCase @Inject constructor(
    private val homeRepository: HomeRepository,
    private val dispatcher: Dispatchers
) {
    suspend operator fun invoke() = withContext(dispatcher.IO) {
        homeRepository.fetchGenres()
    }
}