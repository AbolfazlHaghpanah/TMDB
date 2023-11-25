package com.example.tmdb.core.network

import kotlinx.serialization.Serializable
import java.io.Serial

@Serializable
data class ErrorResponse(
    val status_message : String,
    val status_code : Int
)