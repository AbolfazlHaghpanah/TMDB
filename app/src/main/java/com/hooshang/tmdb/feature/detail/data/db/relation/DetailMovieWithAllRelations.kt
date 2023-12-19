package com.hooshang.tmdb.feature.detail.data.db.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.hooshang.tmdb.core.data.model.local.GenreEntity
import com.hooshang.tmdb.core.data.model.local.MovieEntity
import com.hooshang.tmdb.feature.detail.data.db.entity.CreditEntity
import com.hooshang.tmdb.feature.detail.data.db.entity.DetailEntity
import com.hooshang.tmdb.feature.detail.data.db.relation.crossrefrence.DetailMovieWithCreditCrossRef
import com.hooshang.tmdb.feature.detail.data.db.relation.crossrefrence.DetailMovieWithGenreCrossRef
import com.hooshang.tmdb.feature.detail.data.db.relation.crossrefrence.DetailMovieWithSimilarMoviesCrossRef
import com.hooshang.tmdb.feature.detail.data.db.relation.crossrefrence.MovieWithGenreCrossRef
import com.hooshang.tmdb.feature.detail.domain.model.MovieDetailDomainModel
import com.hooshang.tmdb.feature.detail.domain.model.SimilarMovieDomainModel
import com.hooshang.tmdb.feature.favorite.data.model.local.entity.FavoriteMovieEntity

data class DetailMovieWithAllRelations(
    @Embedded val detailEntity: DetailEntity,
    @Relation(
        parentColumn = "detailMovieId",
        entityColumn = "genreId",
        associateBy = Junction(DetailMovieWithGenreCrossRef::class)
    )
    val genres: List<GenreEntity>?,
    @Relation(
        parentColumn = "detailMovieId",
        entityColumn = "creditId",
        associateBy = Junction(DetailMovieWithCreditCrossRef::class)
    )
    val credits: List<CreditEntity>?,
    @Relation(
        parentColumn = "detailMovieId",
        entityColumn = "id",
        entity = MovieEntity::class,
        associateBy = Junction(DetailMovieWithSimilarMoviesCrossRef::class)
    )
    val similarMovies: List<SimilarMovieWithGenre>?,
    @Relation(
        parentColumn = "detailMovieId",
        entityColumn = "id"
    )
    val movie: MovieEntity?,
    @Relation(
        parentColumn = "detailMovieId",
        entityColumn = "movieId"
    )
    val favorite: FavoriteMovieEntity?
) {
    fun toMovieDetail(): MovieDetailDomainModel = MovieDetailDomainModel(
        id = detailEntity.detailMovieId,
        title = movie?.title ?: "",
        overview = detailEntity.overview,
        voteAverage = movie?.voteAverage ?: 0.0,
        posterPath = movie?.posterPath ?: "",
        releaseDate = detailEntity.releaseDate.split("-")[0],
        genres = genres?.map { Pair(it.genreId, it.genreName) } ?: listOf(),
        credits = credits?.map { it.toCastOrCrew() } ?: listOf(),
        runtime = detailEntity.runtime,
        externalIds = detailEntity.externalIds,
        similar = similarMovies?.map { it.toSimilarMovie() } ?: listOf(),
        isFavorite = favorite != null
    )
}

data class SimilarMovieWithGenre(
    @Embedded val similarMovie: MovieEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "genreId",
        entity = GenreEntity::class,
        associateBy = Junction(MovieWithGenreCrossRef::class)
    )
    val genres: List<GenreEntity>
) {
    fun toSimilarMovie(): SimilarMovieDomainModel = SimilarMovieDomainModel(
        id = similarMovie.id,
        posterPath = similarMovie.posterPath,
        voteAverage = similarMovie.voteAverage.toFloat(),
        title = similarMovie.title,
        genreIds = genres.joinToString(separator = "|") { it.genreName }
    )
}