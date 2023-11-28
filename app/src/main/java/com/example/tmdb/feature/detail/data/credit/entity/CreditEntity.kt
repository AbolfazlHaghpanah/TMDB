package com.example.tmdb.feature.detail.data.credit.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "credits")
data class CreditEntity(
    @PrimaryKey val creditId: Int,
    val name: String,
    val profilePath: String?,
    val job: String? = null
)
