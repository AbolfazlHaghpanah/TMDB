package com.example.tmdb.feature.favorite.data.local.localdatasource

import com.example.tmdb.feature.favorite.data.local.dao.FavoriteMovieDao
import com.example.tmdb.feature.favorite.domain.model.FavoriteMovieDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteLocalDataSource @Inject constructor(
    private val favoriteMovieDao: FavoriteMovieDao
) {

    fun getFavoriteMovies(): Flow<List<FavoriteMovieDomainModel>> {
        return favoriteMovieDao.observeMovies()
            .map { movie ->
                movie.map { it.toDomainModel() }
            }
    }

    fun deleteMovie(id : Int) {
        favoriteMovieDao.deleteMovie(id)
    }
}