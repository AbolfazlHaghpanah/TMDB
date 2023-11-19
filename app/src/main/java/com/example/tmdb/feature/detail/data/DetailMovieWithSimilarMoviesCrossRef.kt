package com.example.tmdb.feature.detail.data

import androidx.room.Entity

@Entity(primaryKeys = ["detailMovieId", "movieId"])
data class DetailMovieWithSimilarMoviesCrossRef(
    val detailMovieId: Int,
    val movieId: Int
)
