package com.example.tmdb.feature.search.network.json

import com.example.tmdb.core.data.moviedata.entity.MovieEntity
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
    fun toMovieEntity(): MovieEntity {
        return MovieEntity(
            id = id,
            title = title,
            voteAverage = voteAverage.toDouble(),
            posterPath = posterPath ?: "",
            backdropPath = ""
        )
    }
}