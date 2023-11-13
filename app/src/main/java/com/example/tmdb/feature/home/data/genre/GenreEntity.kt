package com.example.tmdb.feature.home.data.genre

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "GENRE")
data class GenreEntity(
    @PrimaryKey val id : Int,
    val genre: String
)