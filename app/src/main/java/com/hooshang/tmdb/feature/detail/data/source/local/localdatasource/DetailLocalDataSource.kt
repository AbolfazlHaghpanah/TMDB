package com.hooshang.tmdb.feature.detail.data.source.local.localdatasource

import com.hooshang.tmdb.core.data.model.local.MovieEntity
import com.hooshang.tmdb.core.data.source.local.MovieDao
import com.hooshang.tmdb.feature.detail.data.model.local.entity.CreditEntity
import com.hooshang.tmdb.feature.detail.data.model.local.entity.DetailEntity
import com.hooshang.tmdb.feature.detail.data.model.local.relation.DetailMovieWithAllRelations
import com.hooshang.tmdb.feature.detail.data.model.local.relation.crossrefrence.DetailMovieWithCreditCrossRef
import com.hooshang.tmdb.feature.detail.data.model.local.relation.crossrefrence.DetailMovieWithGenreCrossRef
import com.hooshang.tmdb.feature.detail.data.model.local.relation.crossrefrence.DetailMovieWithSimilarMoviesCrossRef
import com.hooshang.tmdb.feature.detail.data.model.local.relation.crossrefrence.MovieWithGenreCrossRef
import com.hooshang.tmdb.feature.detail.data.source.local.dao.DetailDao
import com.hooshang.tmdb.feature.favorite.data.model.local.entity.FavoriteMovieEntity
import com.hooshang.tmdb.feature.favorite.data.model.local.relation.FavoriteMovieGenreCrossRef
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

    suspend fun insertFavoriteMovieGenre(genre: FavoriteMovieGenreCrossRef) {
        return detailDao.insertFavoriteMovieGenre(genre)
    }

    fun insertDetailMovieWithCredits(detailMovieWithCreditCrossRef: List<DetailMovieWithCreditCrossRef>) {
        return detailDao.insertDetailMovieWithCredits(detailMovieWithCreditCrossRef)
    }

    fun insertDetailMovieWithGenres(detailMovieWithGenreCrossRef: List<DetailMovieWithGenreCrossRef>) {
        return detailDao.insertDetailMovieWithGenres(detailMovieWithGenreCrossRef)
    }

    fun insertDetailMovieWithSimilarMovies(detailMovieWithSimilarMoviesCrossRef: List<DetailMovieWithSimilarMoviesCrossRef>) {
        return detailDao.insertDetailMovieWithSimilarMovies(
            detailMovieWithSimilarMoviesCrossRef
        )
    }

    fun insertMovieWithGenres(movieWithGenre: List<MovieWithGenreCrossRef>) {
        return detailDao.insertMovieWithGenres(movieWithGenre)
    }

    fun addCredits(credit: List<CreditEntity>) {
        return detailDao.insertCredits(credit)
    }

    suspend fun addToFavorite(movieEntity: FavoriteMovieEntity) {
        return detailDao.addToFavorite(movieEntity)
    }

    suspend fun insertMovies(movie: List<MovieEntity>) {
        return movieDao.insertMovies(movie)
    }
}