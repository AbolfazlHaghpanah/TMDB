package com.example.tmdb.feature.detail.network.json

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetail(
    @SerialName("original_title")
    val originalTitle: String,
    val overview: String,
    @SerialName("vote_average")
    val voteAverage: Float,
    @SerialName("poster_path")
    val posterPath: String,
    @SerialName("release_date")
    val releaseDate: String,
    val runtime: Int,
    val genres: List<Genre>,
    @SerialName("external_ids")
    val externalIds: ExternalIds?,
    val credits: CastWithCrew,
    val similar: SimilarResults
)

@Serializable
data class Genre(
    val name: String,
    val id: Int
)

@Serializable
data class ExternalIds(
    @SerialName("imdb_id")
    val imdbId: String?,
    @SerialName("instagram_id")
    val instagramId: String?,
    @SerialName("twitter_id")
    val twitterId: String?
)

@Serializable
data class CastWithCrew(
    val cast: List<CastOrCrew>,
    val crew: List<CastOrCrew>
)


@Serializable
data class CastOrCrew(
    val name: String,
    @SerialName("profile_path")
    val profilePath: String?,
    val job: String? = null
)

@Serializable
data class SimilarResults(
    val results: List<SimilarMovieResult>
)

@Serializable
data class SimilarMovieResult(
    @SerialName("genre_ids")
    val genreIds: List<Int>,
    @SerialName("original_title")
    val originalTitle: String,
    @SerialName("vote_average")
    val voteAverage: Float,
    @SerialName("poster_path")
    val posterPath: String?
)