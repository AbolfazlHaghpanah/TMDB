package com.hooshang.tmdb.feature.detail.data.datasource.localdatasource

import com.hooshang.tmdb.core.data.model.local.MovieEntity
import com.hooshang.tmdb.feature.detail.data.db.entity.CreditEntity
import com.hooshang.tmdb.feature.detail.data.db.entity.DetailEntity
import com.hooshang.tmdb.feature.detail.data.db.relation.DetailMovieWithAllRelations
import com.hooshang.tmdb.feature.detail.data.db.relation.crossrefrence.DetailMovieWithCreditCrossRef
import com.hooshang.tmdb.feature.detail.data.db.relation.crossrefrence.DetailMovieWithGenreCrossRef
import com.hooshang.tmdb.feature.detail.data.db.relation.crossrefrence.DetailMovieWithSimilarMoviesCrossRef
import com.hooshang.tmdb.feature.detail.data.db.relation.crossrefrence.MovieWithGenreCrossRef
import com.hooshang.tmdb.feature.favorite.data.model.local.entity.FavoriteMovieEntity
import com.hooshang.tmdb.feature.favorite.data.model.local.relation.FavoriteMovieGenreCrossRef
import kotlinx.coroutines.flow.Flow

interface DetailLocalDataSource {
    fun getMovieDetail(detailMovieId: Int): DetailMovieWithAllRelations?

    fun observeExistInFavorite(id: Int): Flow<Boolean>

    suspend fun insertMovieDetails(detailEntity: DetailEntity)

    suspend fun insertDetailMoviesWithGenres(detailMovieWithGenreCrossRef: List<DetailMovieWithGenreCrossRef>)

    suspend fun insertFavoriteMovieGenre(genres: List<FavoriteMovieGenreCrossRef>)

    suspend fun insertDetailMoviesWithSimilarMovies(detailMovieWithSimilarMoviesCrossRef: List<DetailMovieWithSimilarMoviesCrossRef>)

    suspend fun insertMoviesWithGenres(movieWithGenre: List<MovieWithGenreCrossRef>)

    suspend fun insertCredits(credit: List<CreditEntity>)

    suspend fun insertDetailMoviesWithCredits(detailMovieWithCreditCrossRef: List<DetailMovieWithCreditCrossRef>)

    suspend fun addToFavorite(movieEntity: FavoriteMovieEntity)

    suspend fun insertMovies(movie: List<MovieEntity>)
}