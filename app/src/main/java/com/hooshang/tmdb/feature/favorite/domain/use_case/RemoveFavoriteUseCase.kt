package com.hooshang.tmdb.feature.favorite.domain.use_case

import com.hooshang.tmdb.feature.favorite.domain.repository.FavoriteRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class RemoveFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
    private val coroutineContext: CoroutineContext
) {
    suspend operator fun invoke(id: Int) = withContext(coroutineContext) {
        favoriteRepository.removeFavorite(id)
    }
}