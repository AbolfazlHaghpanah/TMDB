package com.example.tmdb.feature.home.data.popularMovie.relation

import androidx.room.Entity

@Entity(primaryKeys = ["genreId", "movieId"])
data class PopularMovieGenreCrossRef(
    val movieId: Int,
    val genreId: Int
)