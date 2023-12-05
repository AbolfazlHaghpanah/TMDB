package com.hooshang.tmdb.feature.favorite.domain.use_case

import com.hooshang.tmdb.feature.favorite.domain.model.FavoriteMovieDomainModel
import com.hooshang.tmdb.feature.favorite.domain.repository.FavoriteRepository
import com.hooshang.tmdb.feature.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow

class GetFavoriteUseCase(
    private val favoriteRepository: FavoriteRepository
) {

    suspend operator fun invoke(): Flow<List<FavoriteMovieDomainModel>> {
        return favoriteRepository.getFavorites()
    }
}