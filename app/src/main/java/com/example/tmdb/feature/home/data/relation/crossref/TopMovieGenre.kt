package com.example.tmdb.feature.home.data.relation.crossref

import androidx.room.Entity

@Entity(primaryKeys = ["genreId", "movieId"])
data class TopMovieGenreCrossRef(
    val movieId: Int,
    val genreId: Int
)