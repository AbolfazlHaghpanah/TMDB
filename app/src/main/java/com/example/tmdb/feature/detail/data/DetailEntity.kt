package com.example.tmdb.feature.detail.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "detail_movies")
data class DetailEntity(
    @PrimaryKey val detailMovieId: Int,
    val overview: String,
    val releaseDate: String,
    val runtime: Int,
    val externalIds: List<String>
)
