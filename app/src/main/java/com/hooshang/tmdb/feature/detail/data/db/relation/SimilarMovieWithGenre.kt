package com.hooshang.tmdb.feature.detail.data.db.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.hooshang.tmdb.core.data.model.local.GenreEntity
import com.hooshang.tmdb.core.data.model.local.MovieEntity
import com.hooshang.tmdb.feature.detail.data.db.relation.crossrefrence.MovieWithGenreCrossRef
import com.hooshang.tmdb.feature.detail.domain.model.SimilarMovieDomainModel

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