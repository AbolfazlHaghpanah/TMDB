package com.example.tmdb.feature.detail.data

import androidx.room.Entity

@Entity(primaryKeys = ["movieId", "genreId"])
data class MovieWithGenreCrossRef(
    val movieId: Int,
    val genreId: Int
)
