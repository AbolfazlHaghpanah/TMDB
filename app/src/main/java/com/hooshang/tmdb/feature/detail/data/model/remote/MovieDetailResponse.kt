package com.hooshang.tmdb.feature.detail.data.model.remote

import com.hooshang.tmdb.feature.detail.data.model.local.entity.CreditEntity
import com.hooshang.tmdb.feature.detail.data.model.local.entity.DetailEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailResponse(
    val id: Int,
    val title: String,
    val overview: String,
    @SerialName("vote_average")
    val voteAverage: Float,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("release_date")
    val releaseDate: String,
    val runtime: Int,
    @SerialName("genres")
    val genreResponses: List<GenreResponse>,
    @SerialName("external_ids")
    val externalIdsResponse: ExternalIdsResponse?,
    val credits: CastWithCrewResponse,
    val similar: SimilarResultsResponse
) {
    fun toDetailEntity(): DetailEntity {
        return DetailEntity(
            detailMovieId = id,
            overview = overview,
            releaseDate = releaseDate,
            runtime = runtime,
            externalIds = listOf(
                "${externalIdsResponse?.imdbId}",
                "${externalIdsResponse?.instagramId}",
                "${externalIdsResponse?.twitterId}"
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