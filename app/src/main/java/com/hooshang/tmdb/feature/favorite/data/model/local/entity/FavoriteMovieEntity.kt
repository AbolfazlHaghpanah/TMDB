package com.hooshang.tmdb.feature.favorite.data.model.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("FAVORITE_MOVIE")
data class FavoriteMovieEntity(
    @PrimaryKey val movieId : Int
)