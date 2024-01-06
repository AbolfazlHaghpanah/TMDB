package com.hooshang.tmdb.feature.home.data.network.model

import androidx.annotation.Keep
import com.hooshang.tmdb.core.data.db.entity.MovieEntity
import com.hooshang.tmdb.core.utils.dateConvertor
import com.hooshang.tmdb.feature.home.data.db.entity.NowPlayingEntity
import com.hooshang.tmdb.feature.home.data.db.entity.PopularMovieEntity
import com.hooshang.tmdb.feature.home.data.db.entity.TopMovieEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
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
    fun toMovieEntity(): MovieEntity = MovieEntity(
        id = id,
        title = title,
        posterPath = posterPath ?: "",
        voteAverage = voteAverage ?: 0.0,
        backdropPath = backdropPath ?: ""
    )

    fun toPopularMovieEntity(): PopularMovieEntity = PopularMovieEntity(
        movieId = id,
    )

    fun toTopPlayingEntity(): TopMovieEntity = TopMovieEntity(
        movieId = id,
    )

    fun toNowPlayingEntity(): NowPlayingEntity = NowPlayingEntity(
        movieId = id,
        releaseDate = dateConvertor(releaseDate ?: "0000,00,00"),
    )
}