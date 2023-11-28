package com.example.tmdb.feature.home.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TOP_MOVIE")
data class TopMovieEntity(
    @PrimaryKey val movieId : Int,
)
