package com.example.tmdb.core.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MOVIES")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val posterPath: String,
    val voteAverage: Double,
    val backdropPath: String
)