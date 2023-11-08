package com.example.tmdb.core.ui.theme.designsystem

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

    val subtitle1: TextStyle = TextStyle(
        fontFamily = montserratFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    ),

    val subtitle2: TextStyle = TextStyle(
        fontFamily = montserratFont,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),

    val body1: TextStyle = TextStyle(
        fontFamily = montserratFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp
    ),

    val body2: TextStyle = TextStyle(
        fontFamily = montserratFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp
    ),

    val button: TextStyle = TextStyle(
        fontFamily = montserratFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    ),

    val caption: TextStyle = TextStyle(
        fontFamily = montserratFont,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp
    ),

    val overLine: TextStyle = TextStyle(
        fontFamily = montserratFont,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp
    )
) {
    @Composable
    fun toTypography(): Typography {
        return MaterialTheme.typography.copy(
            h6 = h6,
            subtitle1 = subtitle1,
            subtitle2 = subtitle2,
            body1 = body1,
            body2 = body2,
            button = button,
            caption = caption,
            overline = overLine
        )
    }
}