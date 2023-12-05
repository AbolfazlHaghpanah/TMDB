package com.example.tmdb.core.data.model.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    @SerialName("status_message")
    val statusMessage : String,

    @SerialName("status_code")
    val statusCode : Int
)