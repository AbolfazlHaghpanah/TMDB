package com.hooshang.tmdb.feature.detail.data.db.converter

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ListStringToStringConverter {
    @TypeConverter
    fun fromListString(list: List<String>): String = Json.encodeToString(list)

    @TypeConverter
    fun toListString(str: String): List<String> = Json.decodeFromString(str)
}
