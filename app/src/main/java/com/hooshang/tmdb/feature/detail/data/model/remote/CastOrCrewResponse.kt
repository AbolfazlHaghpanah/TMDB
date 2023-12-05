package com.hooshang.tmdb.feature.detail.data.model.remote

import com.hooshang.tmdb.feature.detail.data.model.local.entity.CreditEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CastOrCrewResponse(
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