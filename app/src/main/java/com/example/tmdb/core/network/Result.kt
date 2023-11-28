package com.example.tmdb.core.network

sealed class Result {
    data object Loading : Result()
    data class Error(val message: String) : Result()
    data class Success<T>(val response: T) : Result()
}
