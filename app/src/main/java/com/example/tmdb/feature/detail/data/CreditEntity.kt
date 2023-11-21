package com.example.tmdb.feature.detail.data

import androidx.room.Entity
import androidx.room.PrimaryKey

//TODO Unused
@Entity(tableName = "credits")
data class CreditEntity(
    @PrimaryKey val creditId: Int,
    val name: String,
    val profilePath: String?,
    val job: String? = null
)
