package com.example.tmdb.core.data

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class DatabaseTypeConvertor {
    @TypeConverter
    fun getGenreFromJson(genres: List<Int>): String {
        return Json.encodeToString(genres.map { it.toString() })
    }

    @TypeConverter
    fun fromImagesJson(json: String): List<Int> {
        return Json.decodeFromString<List<String>>(json).map { it.toInt() }
    }
}