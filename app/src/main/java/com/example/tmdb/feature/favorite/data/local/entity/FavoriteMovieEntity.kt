package com.example.tmdb.feature.favorite.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("FAVORITE_MOVIE")
data class FavoriteMovieEntity(
    @PrimaryKey val movieId : Int
)