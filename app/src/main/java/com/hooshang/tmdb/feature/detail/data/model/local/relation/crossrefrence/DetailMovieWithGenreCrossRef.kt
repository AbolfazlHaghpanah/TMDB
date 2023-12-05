package com.hooshang.tmdb.feature.detail.data.model.local.relation.crossrefrence

import androidx.room.Entity

@Entity(primaryKeys = ["detailMovieId", "genreId"])
data class DetailMovieWithGenreCrossRef(
    val detailMovieId: Int,
    val genreId: Int
)
