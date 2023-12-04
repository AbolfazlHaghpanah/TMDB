package com.example.tmdb.feature.detail.data.source.local

import com.example.tmdb.core.data.movie.dao.MovieDao
import com.example.tmdb.core.data.movie.entity.MovieEntity
import com.example.tmdb.feature.detail.data.source.local.dao.DetailDao
import com.example.tmdb.feature.detail.data.source.local.entity.CreditEntity
import com.example.tmdb.feature.detail.data.source.local.entity.DetailEntity
import com.example.tmdb.feature.detail.data.source.local.relation.DetailMovieWithAllRelations
import com.example.tmdb.feature.detail.data.source.local.relation.crossrefrence.DetailMovieWithCreditCrossRef
import com.example.tmdb.feature.detail.data.source.local.relation.crossrefrence.DetailMovieWithGenreCrossRef
import com.example.tmdb.feature.detail.data.source.local.relation.crossrefrence.DetailMovieWithSimilarMoviesCrossRef
import com.example.tmdb.feature.detail.data.source.local.relation.crossrefrence.MovieWithGenreCrossRef
import com.example.tmdb.feature.favorite.data.entity.FavoriteMovieEntity
import com.example.tmdb.feature.favorite.data.relation.FavoriteMovieGenreCrossRef
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DetailLocalDataSource @Inject constructor(
    private val detailDao: DetailDao,
    private val movieDao: MovieDao
) {
    fun observeMovieDetail(detailMovieId: Int): Flow<DetailMovieWithAllRelations?> {
        return detailDao.observeMovieDetail(detailMovieId)
    }

    suspend fun addDetail(detailEntity: DetailEntity) {
        return detailDao.addDetail(detailEntity)
    }

    suspend fun addFavoriteMovieGenre(genre: FavoriteMovieGenreCrossRef) {
        return detailDao.addFavoriteMovieGenre(genre)
    }

    suspend fun deleteFavoriteMovieGenre(favoriteMovieGenreCrossRef: FavoriteMovieGenreCrossRef) {
        return detailDao.deleteFavoriteMovieGenre(favoriteMovieGenreCrossRef)
    }

    fun addDetailMovieWithCreditCrossRef(detailMovieWithCreditCrossRef: DetailMovieWithCreditCrossRef) {
        return detailDao.addDetailMovieWithCreditCrossRef(detailMovieWithCreditCrossRef)
    }

    fun addDetailMovieWithGenreCrossRef(detailMovieWithGenreCrossRef: DetailMovieWithGenreCrossRef) {
        return detailDao.addDetailMovieWithGenreCrossRef(detailMovieWithGenreCrossRef)
    }

    fun addDetailMovieWithSimilarMoviesCrossRef(detailMovieWithSimilarMoviesCrossRef: DetailMovieWithSimilarMoviesCrossRef) {
        return detailDao.addDetailMovieWithSimilarMoviesCrossRef(
            detailMovieWithSimilarMoviesCrossRef
        )
    }

    fun addMovieWithGenreCrossRef(movieWithGenre: MovieWithGenreCrossRef) {
        return detailDao.addMovieWithGenreCrossRef(movieWithGenre)
    }

    fun addCredits(credit: List<CreditEntity>) {
        return detailDao.addCredits(credit)
    }

    suspend fun deleteFavorite(movieEntity: FavoriteMovieEntity) {
        return detailDao.deleteFavorite(movieEntity)
    }

    suspend fun addToFavorite(movieEntity: FavoriteMovieEntity) {
        return detailDao.addToFavorite(movieEntity)
    }

    suspend fun addMovie(movie: MovieEntity) {
        return movieDao.addMovie(movie)
    }
}