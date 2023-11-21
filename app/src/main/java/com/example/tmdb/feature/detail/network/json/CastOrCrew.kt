package com.example.tmdb.feature.detail.network.json

import com.example.tmdb.feature.detail.data.credit.CreditEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CastOrCrew(
    val id: Int,
    val name: String,
    @SerialName("profile_path")
    val profilePath: String?,
    val job: String? = null
) {
    fun toCreditEntity(): CreditEntity {
        return CreditEntity(
            creditId = id,
            name = name,
            job = job,
            profilePath = profilePath
        )
    }
}