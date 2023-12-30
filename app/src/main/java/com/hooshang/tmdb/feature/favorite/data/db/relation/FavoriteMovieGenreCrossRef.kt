package com.hooshang.tmdb.feature.favorite.data.db.relation

import androidx.room.Entity

@Entity(primaryKeys = ["movieId", "genreId"])
data class FavoriteMovieGenreCrossRef(
    val movieId: Int,
    val genreId: Int
)