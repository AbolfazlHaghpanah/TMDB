package com.hooshang.tmdb.feature.favorite.domain.use_case

import com.hooshang.tmdb.feature.favorite.domain.model.FavoriteMovieDomainModel
import com.hooshang.tmdb.feature.favorite.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ObserveFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
    private val coroutineContext: CoroutineContext
) {
    suspend operator fun invoke(): Flow<List<FavoriteMovieDomainModel>> =
        withContext(coroutineContext) {
            favoriteRepository.observeFavorites()
        }
}