package com.example.tmdb.feature.home.data.topmovies

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TOP_MOVIE")
data class TopMovieEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val movieId : Int,
    val title: String,
    val posterPath : String,
    val releaseDate: String,
    val genreIds: List<Int>,
    val voteAverage : Double
)