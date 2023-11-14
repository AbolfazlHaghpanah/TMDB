package com.example.tmdb.feature.home.data.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.tmdb.feature.home.data.common.MovieWithGenreDatabaseWrapper
import com.example.tmdb.feature.home.data.genre.entity.GenreEntity
import com.example.tmdb.feature.home.data.movie.entity.TopMovieEntity
import com.example.tmdb.feature.home.data.relation.crossref.TopMovieGenreCrossRef

data class TopMovieWithGenre(
    @Embedded val movie: TopMovieEntity,
    @Relation(
        parentColumn = "movieId",
        entity = GenreEntity::class,
        entityColumn = "genreId",
        associateBy = Junction(TopMovieGenreCrossRef::class)
    )
    val genres: List<GenreEntity>
) {
    fun toWrapper(): MovieWithGenreDatabaseWrapper {
        return MovieWithGenreDatabaseWrapper(
            movie = movie.toMovieDataWrapper(),
            genres = genres
        )
    }
}