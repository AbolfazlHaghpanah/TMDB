package com.example.tmdb.core.ui.shimmer

import com.example.tmdb.feature.home.ui.model.HomeMovieUiModel
import kotlinx.collections.immutable.persistentListOf

val fakeMovie = persistentListOf(
    HomeMovieUiModel(
        "", "//////////////////", 0, "/////////", "", 0.1,
    ),
    HomeMovieUiModel(
        "", "//////////////////", 1, "/////////", "", 0.1,
    ),HomeMovieUiModel(
        "", "//////////////////", 2, "/////////", "", 0.1,
    )
)