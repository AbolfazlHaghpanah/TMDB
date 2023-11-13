package com.example.tmdb.feature.home.data.movie.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TOP_MOVIE")
data class TopMovieEntity(
    @PrimaryKey val movieId : Int,
    val title: String,
    val posterPath : String,
    val voteAverage : Double
)