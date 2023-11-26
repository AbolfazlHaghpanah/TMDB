package com.example.tmdb.core.utils

import androidx.compose.material.SnackbarDuration
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.annotation.concurrent.Immutable

class SnackBarManager() {
    private val _snackBarMessage: Channel<SnackBarMassage?> = Channel()

    suspend fun sendMessage(
        snackBarMassage: SnackBarMassage?
    ) {
        _snackBarMessage.send(
            if (snackBarMassage?.isHaveToShow == true) snackBarMassage else null
        )
    }

    suspend fun dismissSnackBar() {
        _snackBarMessage.send(null)
    }

    fun getSnackBarMessage() = _snackBarMessage.receiveAsFlow()
}

@Immutable
data class SnackBarMassage(
    val snackBarMessage: String?,
    val snackBarAction: (() -> Unit)? = null,
    val snackBarActionLabel: String? = null,
    val snackBarDuration: SnackbarDuration = SnackbarDuration.Long,
    val isHaveToShow: Boolean = true
) {
    fun getMessage() = snackBarMessage
    fun getActionLabel() = snackBarActionLabel
    fun getDuration() = snackBarDuration

    fun performAction() {
        snackBarAction?.invoke()
    }
}
