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
import com.hooshang.tmdb.feature.detail.domain.model.MovieDetailDomainModel

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
        entityColumn = "id"
    )
    val movie: MovieEntity?,
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
    val similar: List<SimilarMovieWithGenre>?
) {
    fun toDomainModel(): MovieDetailDomainModel = MovieDetailDomainModel(
        id = detailEntity.detailMovieId,
        title = movie?.title.orEmpty(),
        overview = detailEntity.overview,
        voteAverage = movie?.voteAverage ?: 0.0,
        posterPath = movie?.posterPath.orEmpty(),
        releaseDate = detailEntity.releaseDate.split("-")[0],
        genres = genres.orEmpty().map { Pair(it.genreId, it.genreName) },
        credits = credits.orEmpty().map { it.toDomainModel() },
        runtime = detailEntity.runtime,
        externalIds = detailEntity.externalIds,
        similar = similar.orEmpty().map { it.toDomainModel() },
        isFavorite = detailEntity.isFavorite
    )
}