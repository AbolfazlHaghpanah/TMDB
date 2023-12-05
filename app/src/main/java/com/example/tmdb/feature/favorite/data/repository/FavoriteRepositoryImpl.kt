package com.example.tmdb.feature.favorite.data.repository

import com.example.tmdb.feature.favorite.data.source.local.localdatasource.FavoriteLocalDataSource
import com.example.tmdb.feature.favorite.domain.repository.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteLocalDataSource: FavoriteLocalDataSource
) : FavoriteRepository {
    override suspend fun getFavorites() = withContext(Dispatchers.IO) {
        favoriteLocalDataSource.getFavoriteMovies()
    }

    override suspend fun deleteFromFavorite(id: Int) {
        favoriteLocalDataSource.deleteMovie(id)
    }
}