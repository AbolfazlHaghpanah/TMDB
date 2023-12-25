package com.hooshang.tmdb.feature.detail.data.db.relation.crossrefrence

import androidx.room.Entity

@Entity(primaryKeys = ["detailMovieId", "id"])
data class DetailMovieWithSimilarMoviesCrossRef(
    val detailMovieId: Int,
    val id: Int
)
