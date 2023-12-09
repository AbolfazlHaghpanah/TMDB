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
                localDataSource.addFavoriteMovieGenre(
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
        localDataSource.addMovie(
            MovieEntity(
                id = movieDetailDto.id,
                posterPath = movieDetailDto.posterPath ?: "",
                voteAverage = movieDetailDto.voteAverage.toDouble(),
                backdropPath = "",
                title = movieDetailDto.title
            )
        )

        localDataSource.addDetail(movieDetailDto.toDetailEntity())

        localDataSource.addCredits(movieDetailDto.toCreditsEntity())

        movieDetailDto.credits.cast.forEach {
            localDataSource.addDetailMovieWithCreditCrossRef(
                DetailMovieWithCreditCrossRef(
                    detailMovieId = movieDetailDto.id,
                    creditId = it.id
                )
            )
        }
        movieDetailDto.credits.crew.forEach {
            localDataSource.addDetailMovieWithCreditCrossRef(
                DetailMovieWithCreditCrossRef(
                    detailMovieId = movieDetailDto.id,
                    creditId = it.id
                )
            )
        }

        movieDetailDto.genreResponses.forEach {
            localDataSource.addDetailMovieWithGenreCrossRef(
                DetailMovieWithGenreCrossRef(
                    detailMovieId = movieDetailDto.id,
                    genreId = it.id
                )
            )
        }

        movieDetailDto.similar.results.forEach {
            localDataSource.addDetailMovieWithSimilarMoviesCrossRef(
                DetailMovieWithSimilarMoviesCrossRef(
                    detailMovieId = movieDetailDto.id,
                    id = it.id
                )
            )
            localDataSource.addMovie(
                MovieEntity(
                    id = it.id,
                    title = it.title,
                    backdropPath = "",
                    voteAverage = it.voteAverage.toDouble(),
                    posterPath = it.posterPath ?: ""
                )
            )
        }

        movieDetailDto.similar.results.forEach { similarMovieResult ->
            similarMovieResult.genreIds.forEach { genreId ->
                localDataSource.addMovieWithGenreCrossRef(
                    MovieWithGenreCrossRef(
                        id = similarMovieResult.id,
                        genreId = genreId
                    )
                )
            }
        }
    }
}