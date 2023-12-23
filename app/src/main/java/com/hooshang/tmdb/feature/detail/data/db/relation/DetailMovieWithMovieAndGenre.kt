package com.hooshang.tmdb.feature.detail.data.db.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.hooshang.tmdb.core.data.model.local.GenreEntity
import com.hooshang.tmdb.core.data.model.local.MovieEntity
import com.hooshang.tmdb.feature.detail.data.db.entity.DetailEntity
import com.hooshang.tmdb.feature.detail.data.db.relation.crossrefrence.DetailMovieWithGenreCrossRef
import com.hooshang.tmdb.feature.detail.domain.model.MovieDetailDomainModel

data class DetailMovieWithMovieAndGenre(
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
) {
    fun toDomainModel(): MovieDetailDomainModel = MovieDetailDomainModel(
        id = detailEntity.detailMovieId,
        title = movie?.title ?: "",
        overview = detailEntity.overview,
        voteAverage = movie?.voteAverage ?: 0.0,
        posterPath = movie?.posterPath ?: "",
        releaseDate = detailEntity.releaseDate.split("-")[0],
        genres = genres?.map { Pair(it.genreId, it.genreName) } ?: listOf(),
        credits = listOf(),
        runtime = detailEntity.runtime,
        externalIds = detailEntity.externalIds,
        similar = listOf(),
        isFavorite = detailEntity.isFavorite
    )
}