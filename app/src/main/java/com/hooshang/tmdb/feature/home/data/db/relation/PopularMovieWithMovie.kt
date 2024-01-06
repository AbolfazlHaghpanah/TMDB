package com.hooshang.tmdb.feature.home.data.db.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.hooshang.tmdb.core.data.db.entity.GenreEntity
import com.hooshang.tmdb.core.data.db.entity.MovieEntity
import com.hooshang.tmdb.feature.home.data.db.entity.PopularMovieEntity
import com.hooshang.tmdb.feature.home.data.db.relation.crossref.PopularMovieGenreCrossRef
import com.hooshang.tmdb.feature.home.domain.model.HomeMovieDomainModel

data class PopularMovieAndGenreWithMovie(
    @Embedded val popularMovie: PopularMovieEntity?,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "id",
    )
    val movie: MovieEntity?,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "genreId",
        associateBy = Junction(PopularMovieGenreCrossRef::class)
    )
    val genres: List<GenreEntity>?
) {
    fun toDomainModel(): HomeMovieDomainModel = HomeMovieDomainModel(
        genres = genres?.joinToString(separator = "|") { it.genreName } ?: "",
        movieId = movie?.id ?: 1,
        title = movie?.title ?: "",
        posterPath = movie?.posterPath ?: "",
        voteAverage = movie?.voteAverage ?: 0.0,
        releaseDate = "",
        backdropPath = movie?.backdropPath ?: ""
    )
}