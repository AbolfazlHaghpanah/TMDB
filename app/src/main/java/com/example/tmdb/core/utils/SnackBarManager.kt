package com.example.tmdb.core.utils

import androidx.compose.material.SnackbarDuration
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.annotation.concurrent.Immutable

@Immutable
data class SnackBarMessage(
    private val _snackBarMessage: Channel<SnackBarManager?>
) {
    suspend fun sendMessage(
        snackBarManager: SnackBarManager?
    ) {
        _snackBarMessage.send(
            if (snackBarManager?.isHaveToShow == true) snackBarManager else null
        )
    }

    suspend fun dismissSnackBar() {
        _snackBarMessage.send(null)
    }

    fun getSnackBarMessage() = _snackBarMessage.receiveAsFlow()
}

@Immutable
data class SnackBarManager(
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
