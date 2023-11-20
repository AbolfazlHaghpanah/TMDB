package com.example.tmdb.feature.detail.data

import androidx.room.Entity

@Entity(primaryKeys = ["detailMovieId", "genreId"])
data class DetailMovieWithGenreCrossRef(
    val detailMovieId: Int,
    val genreId: Int
)
