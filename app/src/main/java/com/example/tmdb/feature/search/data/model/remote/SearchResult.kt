package com.example.tmdb.feature.search.data.model.remote

import com.example.tmdb.feature.search.domain.model.SearchMovieDomainModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResult(
    val results: List<SearchResultElement>
)

@Serializable
data class SearchResultElement(
    val id: Int,
    val title: String,

    @SerialName("original_language")
    val originalLanguage: String,

    @SerialName("vote_average")
    val voteAverage: Float,

    @SerialName("poster_path")
    val posterPath: String?,

    @SerialName("release_date")
    val releaseDate: String,

    @SerialName("genre_ids")
    val genreIds: List<Int>
) {

    fun toDomainModel(): Pair<SearchMovieDomainModel, List<Int>> {
        return Pair(
            SearchMovieDomainModel(
                id = id,
                title = title,
                backdropPath = "",
                voteAverage = voteAverage,
                posterPath = posterPath ?: "",
                releaseDate = releaseDate,
                originalLanguage = originalLanguage
            ),
            genreIds
        )
    }
}