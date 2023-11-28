package com.example.tmdb.feature.favorite.data.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.tmdb.core.data.genre.entity.GenreEntity
import com.example.tmdb.core.data.moviedata.entity.MovieEntity
import com.example.tmdb.feature.favorite.data.entity.FavoriteMovieEntity
import com.example.tmdb.core.utils.MovieDatabaseWrapper
import com.example.tmdb.core.utils.MovieWithGenreDatabaseWrapper
import kotlinx.collections.immutable.toPersistentList

data class FavoriteMovieWithMovieAndGenre(
    @Embedded val favoriteMovie: FavoriteMovieEntity,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "id"
    ) val movie: MovieEntity,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "genreId",
        associateBy = Junction(FavoriteMovieGenreCrossRef::class)
    ) val genres: List<GenreEntity>
) {
    fun toMovieDatabaseWrapper(): MovieWithGenreDatabaseWrapper {
        return MovieWithGenreDatabaseWrapper(
            genres = genres.toPersistentList(),
            movie = MovieDatabaseWrapper(
                title = movie.title,
                movieId = movie.id,
                posterPath = movie.posterPath,
                voteAverage = movie.voteAverage
            )
        )
    }
}