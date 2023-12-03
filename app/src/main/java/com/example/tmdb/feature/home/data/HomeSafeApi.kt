package com.example.tmdb.feature.home.data

import com.example.tmdb.core.network.ErrorResponse
import com.example.tmdb.core.network.Result
import com.example.tmdb.core.network.mapServerErrorMessage
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.isActive
import kotlinx.serialization.json.Json
import retrofit2.Response

suspend fun <T> homeSafeApi(
    call: suspend () -> Response<T>
): Result {
    try {
        val response = call()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                if (currentCoroutineContext().isActive) {
                    return Result.Success(body)
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
    } catch (t: Throwable) {
        return Result.Error(mapServerErrorMessage((t.message ?: "")))
    }
}