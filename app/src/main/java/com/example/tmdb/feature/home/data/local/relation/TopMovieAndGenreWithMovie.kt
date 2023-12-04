package com.example.tmdb.feature.home.data.local.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.tmdb.core.data.genre.entity.GenreEntity
import com.example.tmdb.core.data.movie.entity.MovieEntity
import com.example.tmdb.feature.home.data.local.entity.TopMovieEntity
import com.example.tmdb.feature.home.data.local.relation.crossref.TopMovieGenreCrossRef
import com.example.tmdb.feature.home.domain.model.HomeMovieDomainModel

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
    fun toDomainModel(): HomeMovieDomainModel {
        return HomeMovieDomainModel(
            genres = genres?.joinToString(separator = "|") { it.genreName }?:"",
            movieId = movie?.id ?: 1,
            title = movie?.title ?: "",
            posterPath = movie?.posterPath ?: "",
            voteAverage = movie?.voteAverage ?: 0.0,
            releaseDate = "",
            backdropPath = movie?.backdropPath ?: ""
        )
    }
}