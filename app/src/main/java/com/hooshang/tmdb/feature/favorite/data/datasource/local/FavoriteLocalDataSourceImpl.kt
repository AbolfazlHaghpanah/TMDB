package com.hooshang.tmdb.feature.favorite.data.datasource.local

import com.hooshang.tmdb.feature.favorite.data.db.dao.FavoriteMovieDao
import com.hooshang.tmdb.feature.favorite.domain.model.FavoriteMovieDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteLocalDataSourceImpl @Inject constructor(
    private val favoriteMovieDao: FavoriteMovieDao
) : FavoriteLocalDataSource {

    override fun observeFavoriteMovies(): Flow<List<FavoriteMovieDomainModel>> =
        favoriteMovieDao.observeMovies()
            .map { movie ->
                movie.map { it.toDomainModel() }
            }

    override fun removeMovie(id: Int) {
        favoriteMovieDao.removeMovie(id)
    }
}