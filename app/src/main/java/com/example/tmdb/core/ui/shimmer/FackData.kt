package com.example.tmdb.core.ui.shimmer

import com.example.tmdb.core.utils.MovieDatabaseWrapper
import com.example.tmdb.core.utils.MovieWithGenreDatabaseWrapper
import com.example.tmdb.core.data.genre.entity.GenreEntity

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