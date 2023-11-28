package com.example.tmdb.feature.home.data.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.tmdb.core.data.genre.entity.GenreEntity
import com.example.tmdb.core.data.moviedata.entity.MovieEntity
import com.example.tmdb.core.utils.MovieDatabaseWrapper
import com.example.tmdb.core.utils.MovieWithGenreDatabaseWrapper
import com.example.tmdb.feature.home.data.entity.TopMovieEntity
import com.example.tmdb.feature.home.data.relation.crossref.TopMovieGenreCrossRef
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

data class TopMovieAndGenreWithMovie(
    @Embedded val topMovie: TopMovieEntity?,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "id"
    )
    val movie: MovieEntity?,
    @Relation(
        parentColumn = "movieId",
        entity = GenreEntity::class,
        entityColumn = "genreId",
        associateBy = Junction(TopMovieGenreCrossRef::class)
    )
    val genres: List<GenreEntity>?
) {
    fun toMovieDataWrapper(): MovieWithGenreDatabaseWrapper {
        return MovieWithGenreDatabaseWrapper(
            genres = genres?.toPersistentList() ?: persistentListOf(),
            movie = MovieDatabaseWrapper(
                movieId = movie?.id ?: 1,
                title = movie?.title ?: "",
                posterPath = movie?.posterPath ?: "",
                voteAverage = movie?.voteAverage ?: 0.0
            )
        )
    }
}