package com.example.tmdb.feature.favorite.data.local.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.tmdb.core.data.genre.entity.GenreEntity
import com.example.tmdb.core.data.movie.entity.MovieEntity
import com.example.tmdb.feature.favorite.data.local.entity.FavoriteMovieEntity
import com.example.tmdb.core.utils.MovieDatabaseWrapper
import com.example.tmdb.core.utils.MovieWithGenreDatabaseWrapper
import com.example.tmdb.feature.favorite.domain.model.FavoriteMovieDomainModel
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

    fun toDomainModel(): FavoriteMovieDomainModel {
        return FavoriteMovieDomainModel(
            id = movie.id,
            title = movie.title,
            genres = genres.joinToString(separator = "|") { it.genreName },
            voteAverage = movie.voteAverage,
            backdropPath = movie.backdropPath
        )
    }

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