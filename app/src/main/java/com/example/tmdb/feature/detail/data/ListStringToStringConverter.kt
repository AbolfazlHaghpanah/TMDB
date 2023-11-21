package com.example.tmdb.feature.detail.data

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
//TODO Unused
class ListStringToStringConverter {
    @TypeConverter
    fun fromListString(list: List<String>): String {
        return Json.encodeToString(list)
    }

    @TypeConverter
    fun toListString(str: String): List<String> {
        return Json.decodeFromString<List<String>>(str)
    }
}
