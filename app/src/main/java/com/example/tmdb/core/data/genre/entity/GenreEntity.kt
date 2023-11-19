package com.example.tmdb.core.data.genre.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "GENRES")
data class GenreEntity(
    @PrimaryKey val genreId: Int,
    val genreName: String
)