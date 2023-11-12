package com.example.tmdb.feature.home.data.popularmovies

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "POPULAR_MOVIES")
data class PopularMovieEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val movieId : Int,
    val title: String,
    val releaseDate: String,
    val backdropPath: String,
    val posterPath : String,
    val genreIds: List<Int>,
    val voteAverage : Double
)