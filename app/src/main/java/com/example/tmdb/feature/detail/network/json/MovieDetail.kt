package com.example.tmdb.feature.detail.network.json

import kotlinx.serialization.Serializable

@Serializable
data class MovieDetail(
    val original_title: String,
    val overview: String,
    val vote_average: Float,
    val poster_path: String,
    val release_date: String,
    val runtime: Int,
    val genres: List<Genres>,
    val external_ids: ExternalIds?,
    val credits: CastWithCrew,
    val similar: SimilarResults
)

@Serializable
data class Genres(
    val name: String
)

@Serializable
data class ExternalIds(
    val imdb_id: String?,
    val instagram_id: String?,
    val twitter_id: String?
)

@Serializable
data class CastWithCrew(
    val cast: List<CastOrCrew>,
    val crew: List<CastOrCrew>
)


@Serializable
data class CastOrCrew(
    val name: String,
    val profile_path: String?,
    val job: String? = null
)

@Serializable
data class SimilarResults(
    val results: List<SimilarMovieResult>
)

@Serializable
data class SimilarMovieResult(
    val genre_ids: List<Int>,
    val original_title: String,
    val vote_average: Float,
    val poster_path: String
)