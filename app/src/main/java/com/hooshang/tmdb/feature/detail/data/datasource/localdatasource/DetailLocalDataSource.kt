package com.hooshang.tmdb.feature.detail.data.datasource.localdatasource

import com.hooshang.tmdb.core.data.model.local.MovieEntity
import com.hooshang.tmdb.core.data.source.local.MovieDao
import com.hooshang.tmdb.feature.detail.data.db.entity.CreditEntity
import com.hooshang.tmdb.feature.detail.data.db.entity.DetailEntity
import com.hooshang.tmdb.feature.detail.data.db.relation.DetailMovieWithAllRelations
import com.hooshang.tmdb.feature.detail.data.db.relation.crossrefrence.DetailMovieWithCreditCrossRef
import com.hooshang.tmdb.feature.detail.data.db.relation.crossrefrence.DetailMovieWithGenreCrossRef
import com.hooshang.tmdb.feature.detail.data.db.relation.crossrefrence.DetailMovieWithSimilarMoviesCrossRef
import com.hooshang.tmdb.feature.detail.data.db.relation.crossrefrence.MovieWithGenreCrossRef
import com.hooshang.tmdb.feature.detail.data.db.dao.DetailDao
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

    suspend fun insertMovieDetails(detailEntity: DetailEntity) {
        return detailDao.insertMovieDetails(detailEntity)
    }

    suspend fun insertFavoriteMovieGenre(genre: FavoriteMovieGenreCrossRef) {
        return detailDao.insertFavoriteMovieGenre(genre)
    }

    fun insertDetailMoviesWithCredits(detailMovieWithCreditCrossRef: List<DetailMovieWithCreditCrossRef>) {
        return detailDao.insertDetailMoviesWithCredits(detailMovieWithCreditCrossRef)
    }

    fun insertDetailMoviesWithGenres(detailMovieWithGenreCrossRef: List<DetailMovieWithGenreCrossRef>) {
        return detailDao.insertDetailMoviesWithGenres(detailMovieWithGenreCrossRef)
    }

    fun insertDetailMoviesWithSimilarMovies(detailMovieWithSimilarMoviesCrossRef: List<DetailMovieWithSimilarMoviesCrossRef>) {
        return detailDao.insertDetailMoviesWithSimilarMovies(
            detailMovieWithSimilarMoviesCrossRef
        )
    }

    fun insertMoviesWithGenres(movieWithGenre: List<MovieWithGenreCrossRef>) {
        return detailDao.insertMoviesWithGenres(movieWithGenre)
    }

    fun insertCredits(credit: List<CreditEntity>) {
        return detailDao.insertCredits(credit)
    }

    suspend fun addToFavorite(movieEntity: FavoriteMovieEntity) {
        return detailDao.addToFavorite(movieEntity)
    }

    suspend fun insertMovies(movie: List<MovieEntity>) {
        return movieDao.insertMovies(movie)
    }

    fun isExistInFavorite(id : Int):Flow<Boolean>  {
        return detailDao.isExistInFavorite(id)
    }
}