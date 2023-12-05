package com.example.tmdb.feature.home.data.remote.json

import com.example.tmdb.core.data.genre.entity.GenreEntity
import kotlinx.serialization.Serializable

//TODO rename to xDto -> GenreResponseDto
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