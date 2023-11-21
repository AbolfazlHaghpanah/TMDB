package com.example.tmdb.feature.detail.data

import androidx.room.Entity
import androidx.room.PrimaryKey
//TODO Unused
@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val movieId: Int,
    val title: String,
    val posterPath: String,
    val voteAverage: Float
)
