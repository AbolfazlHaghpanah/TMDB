package com.hooshang.tmdb.feature.detail.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hooshang.tmdb.feature.detail.domain.model.CastOrCrewDomainModel

@Entity(tableName = "credits")
data class CreditEntity(
    @PrimaryKey val creditId: Int,
    val name: String,
    val profilePath: String?,
    val job: String
) {
    fun toDomainModel(): CastOrCrewDomainModel = CastOrCrewDomainModel(
        id = creditId,
        name = name,
        profilePath = profilePath,
        job = job
    )
}
