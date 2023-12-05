package com.hooshang.tmdb.core.utils

import java.text.SimpleDateFormat
import java.util.Locale

fun dateConvertor(inputDateString: String): String {

    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    val outputFormat = SimpleDateFormat("MMMM d, yyyy", Locale.US)
    val date = inputFormat.parse(inputDateString)

    return outputFormat.format(date)
}