package com.hooshang.tmdb.feature.favorite.domain.use_case

import com.hooshang.tmdb.feature.favorite.domain.repository.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoveFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
    private val dispatcher: Dispatchers
) {
    suspend operator fun invoke(id: Int) = withContext(dispatcher.IO) {
        favoriteRepository.removeFavorite(id)
    }
}