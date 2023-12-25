package com.hooshang.tmdb.feature.detail.data.datasource.localdatasource

import com.hooshang.tmdb.core.data.model.local.MovieEntity
import com.hooshang.tmdb.core.data.source.local.MovieDao
import com.hooshang.tmdb.feature.detail.data.db.dao.DetailDao
import com.hooshang.tmdb.feature.detail.data.db.entity.CreditEntity
import com.hooshang.tmdb.feature.detail.data.db.entity.DetailEntity
import com.hooshang.tmdb.feature.detail.data.db.relation.DetailMovieWithMovieAndGenre
import com.hooshang.tmdb.feature.detail.data.db.relation.SimilarMovieWithGenre
import com.hooshang.tmdb.feature.detail.data.db.relation.crossrefrence.DetailMovieWithCreditCrossRef
import com.hooshang.tmdb.feature.detail.data.db.relation.crossrefrence.DetailMovieWithGenreCrossRef
import com.hooshang.tmdb.feature.detail.data.db.relation.crossrefrence.DetailMovieWithSimilarMoviesCrossRef
import com.hooshang.tmdb.feature.detail.data.db.relation.crossrefrence.MovieWithGenreCrossRef
import com.hooshang.tmdb.feature.favorite.data.model.local.entity.FavoriteMovieEntity
import com.hooshang.tmdb.feature.favorite.data.model.local.relation.FavoriteMovieGenreCrossRef
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DetailLocalDataSourceImpl  @Inject constructor(
    private val detailDao: DetailDao,
    private val movieDao: MovieDao
) : DetailLocalDataSource {
    override fun observeMovieDetail(detailMovieId: Int): Flow<DetailMovieWithMovieAndGenre?> =
        detailDao.observeMovieDetail(detailMovieId)

    override fun observeCredits(id: Int): Flow<List<CreditEntity>> =
        detailDao.observeCredits(id)

    override fun observeSimilar(id: Int): Flow<List<SimilarMovieWithGenre>> =
        detailDao.observeSimilarMovie(id)

    override fun existInFavorite(id: Int): Flow<Boolean> =
        detailDao.existInFavorite(id)

    override suspend fun insertMovieDetails(detailEntity: DetailEntity) =
        detailDao.insertMovieDetails(detailEntity)

    override suspend fun insertDetailMoviesWithGenres(detailMovieWithGenreCrossRef: List<DetailMovieWithGenreCrossRef>) =
        detailDao.insertDetailMoviesWithGenres(detailMovieWithGenreCrossRef)

    override suspend fun insertFavoriteMovieGenre(genres: List<FavoriteMovieGenreCrossRef>) =
        detailDao.insertFavoriteMovieGenre(genres)

    override suspend fun insertDetailMoviesWithSimilarMovies(detailMovieWithSimilarMoviesCrossRef: List<DetailMovieWithSimilarMoviesCrossRef>) =
        detailDao.insertDetailMoviesWithSimilarMovies(detailMovieWithSimilarMoviesCrossRef)

    override suspend fun insertMoviesWithGenres(movieWithGenre: List<MovieWithGenreCrossRef>) =
        detailDao.insertMoviesWithGenres(movieWithGenre)

    override suspend fun insertCredits(credit: List<CreditEntity>) =
        detailDao.insertCredits(credit)

    override suspend fun insertDetailMoviesWithCredits(detailMovieWithCreditCrossRef: List<DetailMovieWithCreditCrossRef>) =
        detailDao.insertDetailMoviesWithCredits(detailMovieWithCreditCrossRef)

    override suspend fun addToFavorite(movieEntity: FavoriteMovieEntity) =
        detailDao.addToFavorite(movieEntity)

    override suspend fun insertMovies(movie: List<MovieEntity>) =
        movieDao.insertMovies(movie)

}