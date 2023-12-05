package com.example.tmdb.core.data.source.remote

import com.example.tmdb.core.data.model.remote.ErrorResponse
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.isActive
import kotlinx.serialization.json.Json
import retrofit2.Response

suspend fun <T> bodyOrThrow(
    call: suspend () -> Response<T>
): T {
    val response = call()
    if (response.isSuccessful) {
        val body = response.body()
        if (body != null) {
            if (currentCoroutineContext().isActive) {
                return body
            } else {
                throw Throwable("lost Scope")
            }
        } else {
            throw Throwable(message = "body was empty")
        }
    } else {
        val bodyCode = response.errorBody()?.string()
            ?.let {
                val json = Json { ignoreUnknownKeys = true }
                json.decodeFromString<ErrorResponse>(it)
            }
        throw Throwable(message = bodyCode?.statusMessage ?: "")
    }
}