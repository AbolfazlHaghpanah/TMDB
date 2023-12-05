package com.example.tmdb.feature.favorite.data.local.relation

import androidx.room.Entity

@Entity(primaryKeys = ["movieId", "genreId"])
data class FavoriteMovieGenreCrossRef(
    val movieId: Int,
    val genreId: Int
)