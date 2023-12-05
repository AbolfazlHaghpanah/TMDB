package com.example.tmdb.feature.home.data.remote.dto

import com.example.tmdb.core.data.genre.entity.GenreEntity
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