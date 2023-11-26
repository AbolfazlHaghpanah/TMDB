package com.example.tmdb.core.utils

import androidx.compose.runtime.Immutable
import com.example.tmdb.core.data.genre.entity.GenreEntity

@Immutable
data class MovieWithGenreDatabaseWrapper(
    val movie : MovieDatabaseWrapper,
    val genres : List<GenreEntity>
)
@Immutable
data class MovieDatabaseWrapper(
    val backdropPath : String = "",
    val releaseDate : String = "",
    val movieId : Int,
    val title: String,
    val posterPath: String,
    val voteAverage: Double
)