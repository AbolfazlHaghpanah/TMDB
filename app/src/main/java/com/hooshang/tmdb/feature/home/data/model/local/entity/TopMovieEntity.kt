package com.hooshang.tmdb.feature.home.data.model.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TOP_MOVIE")
data class TopMovieEntity(
    @PrimaryKey val movieId : Int,
)
