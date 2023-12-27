package com.hooshang.tmdb.feature.detail.domain.usecase

import com.hooshang.tmdb.feature.detail.domain.repository.DetailRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddFavoriteUseCase @Inject constructor(
    private val detailRepository: DetailRepository,
    private val coroutineDispatcher : CoroutineDispatcher
) {
    suspend operator fun invoke(movieId: Int, genres: List<Int>) = withContext(coroutineDispatcher) {
        detailRepository.addToFavorite(movieId, genres)
    }
}