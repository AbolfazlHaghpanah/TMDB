package com.example.tmdb.core.ui.theme.tmdbtheme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.tmdb.R

val montserratFont = FontFamily(Font(R.font.montserrat_regular))

@Immutable
data class TMDBType(
    val h6: TextStyle = TextStyle(
        fontFamily = montserratFont,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),

    val body1: TextStyle = TextStyle(
        fontFamily = montserratFont,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),

    val body2 : TextStyle = TextStyle(
        fontFamily = montserratFont,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    )
){
    @Composable
    fun toTypography() : Typography {
        return MaterialTheme.typography.copy()
    }
}