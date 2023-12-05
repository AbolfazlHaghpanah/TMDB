package com.hooshang.tmdb.feature.favorite.data.model.local.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.hooshang.tmdb.core.data.model.local.GenreEntity
import com.hooshang.tmdb.core.data.model.local.MovieEntity
import com.hooshang.tmdb.feature.favorite.data.model.local.entity.FavoriteMovieEntity
import com.hooshang.tmdb.feature.favorite.domain.model.FavoriteMovieDomainModel

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
}