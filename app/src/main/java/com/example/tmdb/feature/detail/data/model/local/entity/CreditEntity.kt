package com.example.tmdb.feature.detail.data.model.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tmdb.feature.detail.domain.model.CastOrCrewDomainModel

@Entity(tableName = "credits")
data class CreditEntity(
    @PrimaryKey val creditId: Int,
    val name: String,
    val profilePath: String?,
    val job: String? = null
) {
    fun toCastOrCrew(): CastOrCrewDomainModel = CastOrCrewDomainModel(
        id = creditId,
        name = name,
        profilePath = profilePath,
        job = job
    )
}
