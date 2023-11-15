package com.example.tmdb.feature.home.network.json

import com.example.tmdb.feature.home.data.genre.entity.GenreEntity
import kotlinx.serialization.Serializable

@Serializable
data class GenreResponse(
    val genres : List<GenresResponse>
)

@Serializable
data class GenresResponse(
    val id : Int,
    val name : String
){
    fun toGenreEntity(): GenreEntity {
        return GenreEntity(
            genreId = id,
            genreName = name
        )
    }
}