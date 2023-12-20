package com.hooshang.tmdb.feature.favorite.domain.use_case

import com.hooshang.tmdb.feature.favorite.domain.repository.FavoriteRepository
import javax.inject.Inject

class DeleteFromFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(id: Int) {
        favoriteRepository.deleteFromFavorite(id)
    }
}