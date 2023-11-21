package com.example.tmdb.feature.detail.network.json

import com.example.tmdb.feature.detail.data.credit.CreditEntity
import com.example.tmdb.feature.detail.data.detail.DetailEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetail(
    val id: Int,

    @SerialName("original_title")
    val originalTitle: String,

    val overview: String,

    @SerialName("vote_average")
    val voteAverage: Float,

    @SerialName("poster_path")
    val posterPath: String,

    @SerialName("backdrop_path")
    val backdropPath: String,

    @SerialName("release_date")
    val releaseDate: String,

    val runtime: Int,

    val genres: List<Genre>,

    @SerialName("external_ids")
    val externalIds: ExternalIds?,

    val credits: CastWithCrew,

    val similar: SimilarResults
) {
    fun toDetailEntity(): DetailEntity {
        return DetailEntity(
            detailMovieId = id,
            overview = overview,
            releaseDate = releaseDate,
            runtime = runtime,
            externalIds = listOf(
                "${externalIds?.imdbId}",
                "${externalIds?.instagramId}",
                "${externalIds?.twitterId}"
            )
        )
    }

    fun toCreditsEntity(): List<CreditEntity> {
        val castAndCrew = mutableListOf<CreditEntity>()

        credits.cast.forEach {
            castAndCrew.add(it.toCreditEntity())
        }

        credits.crew.forEach {
            castAndCrew.add(it.toCreditEntity())
        }

        return castAndCrew
    }
}