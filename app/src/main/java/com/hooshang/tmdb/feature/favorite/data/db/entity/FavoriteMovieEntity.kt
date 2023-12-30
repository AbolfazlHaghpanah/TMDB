package com.hooshang.tmdb.feature.favorite.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("favorite_movie")
data class FavoriteMovieEntity(
    @PrimaryKey val movieId: Int
)