package com.hooshang.tmdb.feature.home.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "now_playing_movies")
data class NowPlayingEntity(
    @PrimaryKey val movieId: Int,
    val releaseDate: String,
)