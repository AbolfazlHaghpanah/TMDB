package com.hooshang.tmdb.feature.favorite.domain.repository

import com.hooshang.tmdb.feature.favorite.domain.model.FavoriteMovieDomainModel
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    suspend fun observeFavorites(): Flow<List<FavoriteMovieDomainModel>>
    suspend fun removeFavorite(id: Int)
}