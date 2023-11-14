package com.example.tmdb.feature.home.data.common

import androidx.compose.runtime.Immutable
import com.example.tmdb.feature.home.data.genre.entity.GenreEntity

@Immutable
data class MovieWithGenreDatabaseWrapper(
    val movie : MovieDatabaseWrapper,
    val genres : List<GenreEntity>
)
data class MovieDatabaseWrapper(
    val movieId : Int,
    val title: String,
    val posterPath: String,
    val voteAverage: Double
)