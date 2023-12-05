package com.hooshang.tmdb.feature.home.data.model.remote

import com.hooshang.tmdb.core.data.model.local.GenreEntity
import kotlinx.serialization.Serializable

@Serializable
data class GenreResponse(
    val genres: List<GenresResponse>
)

@Serializable
data class GenresResponse(
    val id: Int,
    val name: String
) {
    fun toGenreEntity(): GenreEntity {
        return GenreEntity(
            genreId = id,
            genreName = name
        )
    }
}