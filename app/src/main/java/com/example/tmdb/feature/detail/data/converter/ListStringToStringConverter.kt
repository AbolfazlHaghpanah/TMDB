package com.example.tmdb.feature.detail.data.converter

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ListStringToStringConverter {
    @TypeConverter
    fun fromListString(list: List<String>): String {
        return Json.encodeToString(list)
    }

    @TypeConverter
    fun toListString(str: String): List<String> {
        //TODO remove unused explicit type
        return Json.decodeFromString<List<String>>(str)
    }
}
