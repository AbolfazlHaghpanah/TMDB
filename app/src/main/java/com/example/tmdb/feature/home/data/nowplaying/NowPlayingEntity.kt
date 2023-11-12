package com.example.tmdb.feature.home.data.nowplaying

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "NOW_PLAYING")
data class NowPlayingEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val movieId : Int,
    val title: String,
    val releaseDate: String,
    val backdropPath: String,
)