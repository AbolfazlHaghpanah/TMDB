package com.example.tmdb.feature.favorite.domain.repository

import com.example.tmdb.feature.favorite.domain.model.FavoriteMovieDomainModel
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    suspend fun getFavorites(): Flow<List<FavoriteMovieDomainModel>>
    suspend fun deleteFromFavorite(id: Int)
}