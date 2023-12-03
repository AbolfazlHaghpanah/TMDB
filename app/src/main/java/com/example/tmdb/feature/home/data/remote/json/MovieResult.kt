package com.example.tmdb.feature.home.data.remote.json

import com.example.tmdb.core.data.movie.entity.MovieEntity
import com.example.tmdb.core.utils.dateConvertor
import com.example.tmdb.feature.home.data.local.entity.NowPlayingEntity
import com.example.tmdb.feature.home.data.local.entity.PopularMovieEntity
import com.example.tmdb.feature.home.data.local.entity.TopMovieEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResult(
    val title: String,
    val id: Int,
    @SerialName("release_date")
    val releaseDate: String?,
    @SerialName("backdrop_path")
    val backdropPath: String?,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("genre_ids")
    val genreIds: List<Int>?,
    @SerialName("vote_average")
    val voteAverage: Double?
) {
    fun toMovieEntity(): MovieEntity {
        return MovieEntity(
            id = id,
            title = title,
            posterPath = posterPath ?: "",
            voteAverage = voteAverage ?: 0.0,
            backdropPath = backdropPath ?: ""
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
            releaseDate = dateConvertor(releaseDate ?: "0000,00,00"),
        )
    }
}