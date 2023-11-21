package com.example.tmdb.feature.detail.data.crossrefrence

import androidx.room.Entity

@Entity(primaryKeys = ["detailMovieId", "id"])
data class DetailMovieWithSimilarMoviesCrossRef(
    val detailMovieId: Int,
    val id: Int
)
