package com.hooshang.tmdb.core.data.source.remote

import com.hooshang.tmdb.core.data.model.remote.ErrorResponse
import com.hooshang.tmdb.core.utils.NetworkErrorException
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.isActive
import kotlinx.serialization.json.Json
import retrofit2.Response
import java.io.IOException

suspend fun <T> bodyOrThrow(
    call: suspend () -> Response<T>
): T {
    try {
        val response = call()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                if (currentCoroutineContext().isActive) {
                    return body
                } else {
                    throw NetworkErrorException.LostScope
                }
            } else {
                throw NetworkErrorException.EmptyBodyError
            }
        } else {
            val bodyCode = response.errorBody()?.string()
                ?.let {
                    val json = Json { ignoreUnknownKeys = true }
                    json.decodeFromString<ErrorResponse>(it)
                }
            throw NetworkErrorException.ServerError(bodyCode?.statusCode ?: 0)
        }
    } catch (e: NetworkErrorException) {
        throw e
    } catch (e : IOException){
        throw Exception("Network Error")
    } catch (e : Exception){
        throw Exception("Unexpected error. try again")
    }
}