package com.example.tmdb.core.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

suspend fun <T> resultWrapper(
    action: suspend () -> T
): Flow<Result> {
    return flow {
        emit(Result.Loading)
        try {
            emit(Result.Success(action.invoke()))
        } catch (t: Throwable) {
            emit(Result.Error(t.message ?: "unexpected error"))
        }
    }
}