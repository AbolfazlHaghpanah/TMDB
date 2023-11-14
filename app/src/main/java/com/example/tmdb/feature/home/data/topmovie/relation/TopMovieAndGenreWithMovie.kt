package com.example.tmdb.feature.home.data.topmovie.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.tmdb.core.data.moviedata.MovieEntity
import com.example.tmdb.feature.home.data.topmovie.TopMovieEntity
import com.example.tmdb.feature.home.data.common.MovieDatabaseWrapper
import com.example.tmdb.feature.home.data.common.MovieWithGenreDatabaseWrapper
import com.example.tmdb.feature.home.data.genre.entity.GenreEntity
import com.example.tmdb.feature.home.data.topmovie.relation.crossref.TopMovieGenreCrossRef

data class TopMovieAndGenreWithMovie(
    @Embedded val topMovie: TopMovieEntity,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "id"
    )
    val movie: MovieEntity,
    @Relation(
        parentColumn = "movieId",
        entity = GenreEntity::class,
        entityColumn = "genreId",
        associateBy = Junction(TopMovieGenreCrossRef::class)
    )
    val genres: List<GenreEntity>
) {
    fun toMovieDataWrapper(): MovieWithGenreDatabaseWrapper {
        return MovieWithGenreDatabaseWrapper(
            genres = genres,
            movie = MovieDatabaseWrapper(
                movieId = movie.id,
                title = movie.title,
                posterPath = movie.posterPath,
                voteAverage = movie.voteAverage
            )
        )
    }
}