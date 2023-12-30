package com.hooshang.tmdb.feature.favorite.data.datasource.local

import com.hooshang.tmdb.feature.favorite.domain.model.FavoriteMovieDomainModel
import kotlinx.coroutines.flow.Flow

interface FavoriteLocalDataSource {
    fun observeFavoriteMovies(): Flow<List<FavoriteMovieDomainModel>>
    fun removeMovie(id: Int)
}