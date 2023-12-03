package com.example.tmdb.feature.home.ui.model

import androidx.compose.runtime.Immutable
import com.example.tmdb.core.data.genre.entity.GenreEntity
import kotlinx.collections.immutable.PersistentList


@Immutable
data class HomeMovieUiModel(
    val backdropPath : String = "",
    val releaseDate : String = "",
    val movieId : Int,
    val title: String,
    val posterPath: String,
    val voteAverage: Double,
    val genres : String = ""
)

