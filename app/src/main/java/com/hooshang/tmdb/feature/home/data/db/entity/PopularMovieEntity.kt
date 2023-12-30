package com.hooshang.tmdb.feature.home.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "popular_movies")
data class PopularMovieEntity(
    @PrimaryKey val movieId: Int,
)