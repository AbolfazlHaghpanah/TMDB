package com.example.tmdb.feature.home.data.local.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.tmdb.core.data.genre.entity.GenreEntity
import com.example.tmdb.core.data.movie.entity.MovieEntity
import com.example.tmdb.core.utils.MovieDatabaseWrapper
import com.example.tmdb.core.utils.MovieWithGenreDatabaseWrapper
import com.example.tmdb.feature.home.data.local.entity.PopularMovieEntity
import com.example.tmdb.feature.home.data.local.relation.crossref.PopularMovieGenreCrossRef
import com.example.tmdb.feature.home.ui.model.HomeMovieUiModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

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
    fun toUiModel(): HomeMovieUiModel {

        return HomeMovieUiModel(
            genres = genres?.joinToString(separator = "|") { it.genreName } ?: "",
            movieId = movie?.id ?: 1,
            title = movie?.title ?: "",
            posterPath = movie?.posterPath ?: "",
            voteAverage = movie?.voteAverage ?: 0.0
        )
    }
}