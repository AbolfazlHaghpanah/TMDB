package com.example.tmdb.feature.detail.data.relation.crossrefrence

import androidx.room.Entity

@Entity(primaryKeys = ["id", "genreId"])
data class MovieWithGenreCrossRef(
    val id: Int,
    val genreId: Int
)
