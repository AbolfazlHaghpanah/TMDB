package com.hooshang.tmdb.feature.home.data.model.local.relation.crossref

import androidx.room.Entity

@Entity(primaryKeys = ["genreId", "movieId"])
data class TopMovieGenreCrossRef(
    val movieId: Int,
    val genreId: Int
)