package com.hooshang.tmdb.feature.detail.data.repository

import com.hooshang.tmdb.core.data.model.local.MovieEntity
import com.hooshang.tmdb.core.utils.NetworkErrorException
import com.hooshang.tmdb.feature.detail.data.datasource.localdatasource.DetailLocalDataSource
import com.hooshang.tmdb.feature.detail.data.datasource.remotedatasource.DetailRemoteDataSource
import com.hooshang.tmdb.feature.detail.data.db.relation.crossrefrence.DetailMovieWithCreditCrossRef
import com.hooshang.tmdb.feature.detail.data.db.relation.crossrefrence.DetailMovieWithGenreCrossRef
import com.hooshang.tmdb.feature.detail.data.db.relation.crossrefrence.DetailMovieWithSimilarMoviesCrossRef
import com.hooshang.tmdb.feature.detail.data.db.relation.crossrefrence.MovieWithGenreCrossRef
import com.hooshang.tmdb.feature.detail.domain.model.MovieDetailDomainModel
import com.hooshang.tmdb.feature.detail.domain.repository.DetailRepository
import com.hooshang.tmdb.feature.favorite.data.model.local.entity.FavoriteMovieEntity
import com.hooshang.tmdb.feature.favorite.data.model.local.relation.FavoriteMovieGenreCrossRef
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(
    private val localDataSource: DetailLocalDataSource,
    private val remoteDataSource: DetailRemoteDataSource
) : DetailRepository {
    override suspend fun getMovieDetails(id: Int): MovieDetailDomainModel =
        localDataSource.getMovieDetail(id)?.toDomainModel()
            ?: throw NetworkErrorException.EmptyBodyError

    override suspend fun fetchMovieDetail(id: Int) {
        val movieDetailDto = remoteDataSource.getMovieDetail(id)

        localDataSource.insertMovieDetails(movieDetailDto.toDetailEntity())

        localDataSource.insertMovies(listOf(movieDetailDto.toMovieEntity()))

        localDataSource.insertDetailMoviesWithGenres(
            movieDetailDto.genreResponses.map {
                DetailMovieWithGenreCrossRef(
                    detailMovieId = movieDetailDto.id,
                    genreId = it.id
                )
            }
        )

        localDataSource.insertCredits(movieDetailDto.toCreditsEntity())

        localDataSource.insertDetailMoviesWithCredits(
            movieDetailDto.credits.cast.map {
                DetailMovieWithCreditCrossRef(
                    detailMovieId = movieDetailDto.id,
                    creditId = it.id
                )
            } + movieDetailDto.credits.crew.map {
                DetailMovieWithCreditCrossRef(
                    detailMovieId = movieDetailDto.id,
                    creditId = it.id
                )
            }
        )

        localDataSource.insertDetailMoviesWithSimilarMovies(
            movieDetailDto.similar.results.map {
                DetailMovieWithSimilarMoviesCrossRef(
                    detailMovieId = movieDetailDto.id,
                    id = it.id
                )
            }
        )

        localDataSource.insertMovies(
            movieDetailDto.similar.results.map { similarMovieResult ->
                localDataSource.insertMoviesWithGenres(
                    similarMovieResult.genreIds.map {
                        MovieWithGenreCrossRef(
                            id = similarMovieResult.id,
                            genreId = it
                        )
                    }
                )
                MovieEntity(
                    id = similarMovieResult.id,
                    title = similarMovieResult.title,
                    backdropPath = similarMovieResult.backdropPath ?: "",
                    voteAverage = similarMovieResult.voteAverage.toDouble(),
                    posterPath = similarMovieResult.posterPath ?: ""
                )
            }
        )
    }

    override suspend fun observeExistInFavorite(id: Int) = localDataSource.observeExistInFavorite(id)

    override suspend fun addToFavoriteWithGenre(movieId: Int, genres: List<Int>) {
        localDataSource.insertFavoriteMovieGenre(
            genres.map {
                FavoriteMovieGenreCrossRef(
                    movieId = movieId,
                    genreId = it
                )
            }
        )
        localDataSource.insertToFavorite(FavoriteMovieEntity(movieId))
    }
}
