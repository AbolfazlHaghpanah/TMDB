package com.example.tmdb.feature.home.network.json

import com.example.tmdb.core.utile.dateConvertor
import com.example.tmdb.feature.home.data.nowplaying.NowPlayingEntity
import com.example.tmdb.feature.home.data.popularmovies.PopularMovieEntity
import com.example.tmdb.feature.home.data.topmovies.TopMovieEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames


@Serializable
data class NowPlayingResponse(
    val results: List<MovieResponse>

)

@Serializable
data class MovieResponse(
    val title: String,
    val id: Int,
    val release_date: String,
    val backdrop_path: String,
    val poster_path: String,
    val genre_ids: List<Int>,
    val vote_average: Double
) {
    fun toPopularMovieEntity(): PopularMovieEntity {
        return PopularMovieEntity(
            movieId = id,
            title = title,
            releaseDate = dateConvertor(release_date),
            backdropPath = backdrop_path,
            posterPath = poster_path,
            genreIds = genre_ids,
            voteAverage = vote_average
        )
    }

    fun toTopPlayingEntity():TopMovieEntity{
        return TopMovieEntity(
            movieId = id,
            title = title,
            releaseDate = dateConvertor(release_date),
            posterPath = poster_path,
            genreIds = genre_ids,
            voteAverage = vote_average
        )
    }

    fun toNowPlayingEntity():NowPlayingEntity{
        return NowPlayingEntity(
            movieId = id,
            title = title,
            releaseDate = dateConvertor(release_date),
            backdropPath = backdrop_path
        )
    }
}


