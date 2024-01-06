package com.hooshang.tmdb.core.data.network.response

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class ErrorResponse(
    @SerialName("status_message")
    val statusMessage: String,
    @SerialName("status_code")
    val statusCode: Int
)