package com.example.tmdb.feature.home.data.nowplayingmovie

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "NOW_PLAYING")
data class NowPlayingEntity(
    @PrimaryKey val movieId: Int,
    val releaseDate: String,
)