package com.example.tmdb.feature.detail.data.source.remote.dto

import com.example.tmdb.feature.detail.data.source.local.entity.CreditEntity
import com.example.tmdb.feature.detail.data.source.local.entity.DetailEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailDto(
    val id: Int,
    val title: String,
    val overview: String,
    @SerialName("vote_average")
    val voteAverage: Float,
    @SerialName("poster_path")
    val posterPath: String,
    @SerialName("release_date")
    val releaseDate: String,
    val runtime: Int,
    @SerialName("genres")
    val genreDtos: List<GenreDto>,
    @SerialName("external_ids")
    val externalIdsDto: ExternalIdsDto?,
    val credits: CastWithCrewDto,
    val similar: SimilarResultsDto
) {
    fun toDetailEntity(): DetailEntity {
        return DetailEntity(
            detailMovieId = id,
            overview = overview,
            releaseDate = releaseDate,
            runtime = runtime,
            externalIds = listOf(
                "${externalIdsDto?.imdbId}",
                "${externalIdsDto?.instagramId}",
                "${externalIdsDto?.twitterId}"
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