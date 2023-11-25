package com.example.tmdb.core.network

sealed class Result {

    object Idle : Result()
    object Loading : Result()
    data class Error(
        val message: String
    ) : Result()

    data class Success<T>(
        val response: T
    ) : Result()
}
