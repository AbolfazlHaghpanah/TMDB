package com.example.tmdb.feature.favorite.data.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.tmdb.core.data.genre.entity.GenreEntity
import com.example.tmdb.core.data.moviedata.MovieEntity
import com.example.tmdb.feature.favorite.data.FavoriteMovieEntity
import com.example.tmdb.feature.home.data.common.MovieDatabaseWrapper
import com.example.tmdb.feature.home.data.common.MovieWithGenreDatabaseWrapper

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
            genres = genres,
            movie = MovieDatabaseWrapper(
                title = movie.title,
                releaseDate = movie.backdropPath,
                movieId = movie.id,
                posterPath = movie.posterPath,
                backdropPath = movie.backdropPath,
                voteAverage = movie.voteAverage
            )
        )
    }
}