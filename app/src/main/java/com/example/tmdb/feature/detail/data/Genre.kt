package com.example.tmdb.feature.detail.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genres")
data class Genre(
    @PrimaryKey val genreId: Int,
    val genreName: String
)