package com.example.tmdb.feature.detail.data.model.local.relation.crossrefrence

import androidx.room.Entity

@Entity(primaryKeys = ["detailMovieId", "creditId"])
data class DetailMovieWithCreditCrossRef(
    val detailMovieId: Int,
    val creditId: Int
)
