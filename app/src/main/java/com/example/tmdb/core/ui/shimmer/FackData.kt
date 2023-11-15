package com.example.tmdb.core.ui.shimmer

import com.example.tmdb.feature.home.data.common.MovieDatabaseWrapper
import com.example.tmdb.feature.home.data.common.MovieWithGenreDatabaseWrapper
import com.example.tmdb.feature.home.data.genre.entity.GenreEntity

val fakeMovie = listOf(
    MovieWithGenreDatabaseWrapper(
        MovieDatabaseWrapper("", "//////////////////", 0, "/////////", "", 0.1),
        listOf(
            GenreEntity(-1, "//////"),
            GenreEntity(-1, "//////")
        )
    ),
    MovieWithGenreDatabaseWrapper(
        MovieDatabaseWrapper("", "//////////////////", 0, "/////////", "", 0.1),
        listOf(
            GenreEntity(-1, "//////"),
            GenreEntity(-1, "//////")
        )
    ),
    MovieWithGenreDatabaseWrapper(
        MovieDatabaseWrapper("", "///////////////////", 0, "/////////", "", 0.1),
        listOf(
            GenreEntity(-1, "//////"),
            GenreEntity(-1, "//////")
        )
    )
)