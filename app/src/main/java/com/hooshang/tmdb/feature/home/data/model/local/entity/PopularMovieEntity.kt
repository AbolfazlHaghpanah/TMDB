package com.hooshang.tmdb.feature.home.data.model.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "POPULAR_MOVIES")
data class PopularMovieEntity(
    @PrimaryKey val movieId: Int,
)