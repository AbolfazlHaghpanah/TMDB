package com.example.tmdb.feature.home.data.movie.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "NOW_PLAYING")
data class NowPlayingEntity(
    @PrimaryKey val movieId: Int,
    val title: String,
    val releaseDate: String,
    val backdropPath: String,
)