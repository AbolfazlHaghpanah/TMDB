package com.example.tmdb.feature.detail.data

import androidx.room.Entity
//TODO Unused
@Entity(primaryKeys = ["detailMovieId", "creditId"])
data class DetailMovieWithCreditCrossRef(
    val detailMovieId: Int,
    val creditId: Int
)
