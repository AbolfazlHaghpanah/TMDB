package com.example.tmdb.core.ui.shimmer

import com.example.tmdb.core.utils.MovieDatabaseWrapper
import com.example.tmdb.core.utils.MovieWithGenreDatabaseWrapper
import com.example.tmdb.core.data.genre.entity.GenreEntity
import kotlinx.collections.immutable.persistentListOf

val fakeMovie = persistentListOf(
    MovieWithGenreDatabaseWrapper(
        MovieDatabaseWrapper("", "//////////////////", 0, "/////////", "", 0.1),
        persistentListOf(
            GenreEntity(-1, "//////"),
            GenreEntity(-1, "//////")
        )
    ),
    MovieWithGenreDatabaseWrapper(
        MovieDatabaseWrapper("", "//////////////////", 1, "/////////", "", 0.1),
        persistentListOf(
            GenreEntity(-1, "//////"),
            GenreEntity(-1, "//////")
        )
    ),
    MovieWithGenreDatabaseWrapper(
        MovieDatabaseWrapper("", "///////////////////", 2, "/////////", "", 0.1),
        persistentListOf(
            GenreEntity(-1, "//////"),
            GenreEntity(-1, "//////")
        )
    )
)