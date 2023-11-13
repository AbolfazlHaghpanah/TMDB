package com.example.tmdb.feature.home.data.genre.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "GENRE")
data class GenreEntity(
    @PrimaryKey val genreId : Int,
    val genre: String
)