package com.hooshang.tmdb.feature.home.data.network.model

import androidx.annotation.Keep
import com.hooshang.tmdb.core.data.db.entity.GenreEntity
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class GenreResponse(
    val genres: List<GenresResponse>
)

@Keep
@Serializable
data class GenresResponse(
    val id: Int,
    val name: String
) {
    fun toGenreEntity(): GenreEntity = GenreEntity(
        genreId = id,
        genreName = name
    )
}