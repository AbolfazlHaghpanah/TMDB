package com.example.tmdb.core.network


sealed class Result {

    object Idle : Result()
    object Loading : Result()
    data class Error(val error: String) : Result()
    object Success : Result()
}
