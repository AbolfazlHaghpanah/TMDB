package com.hooshang.tmdb.core.utils

sealed class NetworkErrorException(message: String) : Throwable(message = message) {
    data object EmptyBodyError : NetworkErrorException("response body was empty")
    data class ServerError(val code: Int) : NetworkErrorException("Server Error code : $code")
    data object LostScope : NetworkErrorException("lost scope")
}