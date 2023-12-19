package com.hooshang.tmdb.feature.detail.data.repository

import com.hooshang.tmdb.core.data.model.local.MovieEntity
import com.hooshang.tmdb.feature.detail.data.model.local.relation.crossrefrence.DetailMovieWithCreditCrossRef
import com.hooshang.tmdb.feature.detail.data.model.local.relation.crossrefrence.DetailMovieWithGenreCrossRef
import com.hooshang.tmdb.feature.detail.data.model.local.relation.crossrefrence.DetailMovieWithSimilarMoviesCrossRef
import com.hooshang.tmdb.feature.detail.data.model.local.relation.crossrefrence.MovieWithGenreCrossRef
import com.hooshang.tmdb.feature.detail.data.source.local.localdatasource.DetailLocalDataSource
import com.hooshang.tmdb.feature.detail.data.source.remote.remotedatasource.DetailRemoteDataSource
import com.hooshang.tmdb.feature.detail.domain.repository.DetailRepository
import com.hooshang.tmdb.feature.favorite.data.model.local.entity.FavoriteMovieEntity
import com.hooshang.tmdb.feature.favorite.data.model.local.relation.FavoriteMovieGenreCrossRef
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(
    private val localDataSource: DetailLocalDataSource,
    private val remoteDataSource: DetailRemoteDataSource
) : DetailRepository {
    override suspend fun addToFavorite(movieId: Int, genres: List<Int>) =
        withContext(Dispatchers.IO) {
            genres.forEach {
                localDataSource.insertFavoriteMovieGenre(
                    FavoriteMovieGenreCrossRef(
                        movieId,
                        it
                    )
                )
            }
            localDataSource.addToFavorite(FavoriteMovieEntity(movieId))
        }

    override suspend fun observeDetailMovieWithAllRelations(id: Int) = withContext(Dispatchers.IO) {

        localDataSource.observeMovieDetail(id).filterNotNull().map {
            it.toMovieDetail()
        }
    }

    override suspend fun fetchMovieDetail(id: Int) = withContext(Dispatchers.IO) {
        val movieDetailDto = remoteDataSource.getMovieDetail(id)

        localDataSource.insertMovies(listOf(movieDetailDto.toMovieEntity()))

        localDataSource.addDetail(movieDetailDto.toDetailEntity())

        localDataSource.addCredits(movieDetailDto.toCreditsEntity())

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

        localDataSource.insertDetailMoviesWithGenres(
            movieDetailDto.genreResponses.map {
                DetailMovieWithGenreCrossRef(
                    detailMovieId = movieDetailDto.id,
                    genreId = it.id
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
}
