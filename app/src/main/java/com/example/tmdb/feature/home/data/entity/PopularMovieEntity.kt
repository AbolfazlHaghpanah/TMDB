package com.example.tmdb.feature.home.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "POPULAR_MOVIES")
data class PopularMovieEntity(
    @PrimaryKey val movieId: Int,
)