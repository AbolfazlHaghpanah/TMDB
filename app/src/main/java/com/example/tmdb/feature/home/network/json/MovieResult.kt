package com.example.tmdb.feature.home.network.json

import com.example.tmdb.core.data.moviedata.MovieEntity
import com.example.tmdb.core.utils.dateConvertor
import com.example.tmdb.feature.home.data.nowplayingmovie.NowPlayingEntity
import com.example.tmdb.feature.home.data.popularMovie.PopularMovieEntity
import com.example.tmdb.feature.home.data.topmovie.TopMovieEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResult(
    val title: String,
    val id: Int,
    @SerialName("release_date")
    val releaseDate: String,
    @SerialName("backdrop_path")
    val backdropPath: String,
    @SerialName("poster_path")
    val posterPath: String,
    @SerialName("genre_ids")
    val genreIds: List<Int>,
    @SerialName("vote_average")
    val voteAverage: Double
) {
    fun toMovieEntity(): MovieEntity {
        return MovieEntity(
            id = id,
            title = title,
            posterPath = posterPath,
            voteAverage = voteAverage
        )
    }
    fun toPopularMovieEntity(): PopularMovieEntity {
        return PopularMovieEntity(
            movieId = id,
        )
    }

    fun toTopPlayingEntity(): TopMovieEntity {
        return TopMovieEntity(
            movieId = id,
        )
    }

    fun toNowPlayingEntity(): NowPlayingEntity {
        return NowPlayingEntity(
            movieId = id,
            releaseDate = dateConvertor(releaseDate),
            backdropPath = backdropPath,
        )
    }
}