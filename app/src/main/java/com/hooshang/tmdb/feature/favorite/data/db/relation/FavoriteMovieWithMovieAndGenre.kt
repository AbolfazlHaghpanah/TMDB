package com.hooshang.tmdb.feature.favorite.data.db.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.hooshang.tmdb.core.data.db.entity.GenreEntity
import com.hooshang.tmdb.core.data.db.entity.MovieEntity
import com.hooshang.tmdb.feature.favorite.data.db.entity.FavoriteMovieEntity
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