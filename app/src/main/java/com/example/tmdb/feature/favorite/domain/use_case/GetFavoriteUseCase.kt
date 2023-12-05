package com.example.tmdb.feature.favorite.domain.use_case

import com.example.tmdb.feature.favorite.domain.model.FavoriteMovieDomainModel
import com.example.tmdb.feature.favorite.domain.repository.FavoriteRepository
import com.example.tmdb.feature.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow

class GetFavoriteUseCase(
    private val favoriteRepository: FavoriteRepository
) {

    suspend operator fun invoke(): Flow<List<FavoriteMovieDomainModel>> {
        return favoriteRepository.getFavorites()
    }
}