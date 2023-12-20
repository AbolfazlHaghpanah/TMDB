package com.hooshang.tmdb.feature.home.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "top_movies")
data class TopMovieEntity(
    @PrimaryKey val movieId: Int,
)
