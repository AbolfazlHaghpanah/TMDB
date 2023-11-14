package com.example.tmdb.feature.home.data.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.tmdb.feature.home.data.common.MovieWithGenreDatabaseWrapper
import com.example.tmdb.feature.home.data.genre.entity.GenreEntity
import com.example.tmdb.feature.home.data.movie.entity.PopularMovieEntity
import com.example.tmdb.feature.home.data.relation.crossref.PopularMovieGenreCrossRef

data class PopularMovieWithGenre(
    @Embedded val movie: PopularMovieEntity,
    @Relation(
        parentColumn = "movieId",
        entity = GenreEntity::class,
        entityColumn = "genreId",
        associateBy = Junction(PopularMovieGenreCrossRef::class)
    )
    val genres: List<GenreEntity>
){
    fun toWrapper():MovieWithGenreDatabaseWrapper{
        return MovieWithGenreDatabaseWrapper(
            movie = movie.toMovieDataWrapper(),
            genres = genres
        )
    }
}

