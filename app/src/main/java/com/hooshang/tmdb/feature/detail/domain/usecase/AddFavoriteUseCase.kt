package com.hooshang.tmdb.feature.detail.domain.usecase

import com.hooshang.tmdb.feature.detail.domain.repository.DetailRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class AddFavoriteUseCase @Inject constructor(
    private val repository: DetailRepository,
    private val coroutineContext : CoroutineContext
) {
    suspend operator fun invoke(movieId: Int, genres: List<Int>) = withContext(coroutineContext) {
        repository.addToFavorite(movieId, genres)
    }
}