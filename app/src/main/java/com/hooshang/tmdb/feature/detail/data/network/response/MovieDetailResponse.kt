package com.hooshang.tmdb.feature.detail.data.network.response

import androidx.annotation.Keep
import com.hooshang.tmdb.core.data.model.local.MovieEntity
import com.hooshang.tmdb.feature.detail.data.db.entity.CreditEntity
import com.hooshang.tmdb.feature.detail.data.db.entity.DetailEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.math.RoundingMode

@Keep
@Serializable
data class MovieDetailResponse(
    val id: Int,
    val title: String,
    val overview: String,
    @SerialName("vote_average")
    val voteAverage: Float,
    @SerialName("backdrop_path")
    val backdropPath: String?,
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
    val similar: SimilarResponse
) {
    fun toDetailEntity(): DetailEntity =
        DetailEntity(
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

    fun toCreditsEntity(): List<CreditEntity> =
        credits.crew.map { it.toCreditEntity() } + credits.cast.map { it.toCreditEntity() }

    fun toMovieEntity(): MovieEntity =
        MovieEntity(
            id = id,
            posterPath = posterPath ?: "",
            voteAverage = voteAverage.toBigDecimal()
                .setScale(1, RoundingMode.FLOOR)?.toDouble() ?: 0.0,
            backdropPath = backdropPath ?: "",
            title = title
        )
}