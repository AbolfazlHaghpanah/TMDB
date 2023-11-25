package com.example.tmdb.core.data

import android.database.StaleDataException
import android.database.sqlite.SQLiteException

fun databaseErrorCatchMessage(t: Throwable): String {
    return when(t){
        is SQLiteException -> "An unexpected error occurred while accessing the database. Please try again later."
        is IllegalStateException -> "Database error: The database is not accessible. Please try again later."
        is StaleDataException ->"Data update conflict: The data you are trying to access has been modified. Please refresh and try again"
        else -> "unexpected error occurred while accessing the database"
    }
}