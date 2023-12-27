package com.hooshang.tmdb.feature.favorite.data.repository

import com.hooshang.tmdb.feature.favorite.data.datasource.local.FavoriteLocalDataSourceImpl
import com.hooshang.tmdb.feature.favorite.domain.repository.FavoriteRepository
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteLocalDataSource: FavoriteLocalDataSourceImpl
) : FavoriteRepository {
    override suspend fun observeFavorites() = favoriteLocalDataSource.observeFavoriteMovies()

    override suspend fun removeFavorite(id: Int) = favoriteLocalDataSource.removeMovie(id)
}