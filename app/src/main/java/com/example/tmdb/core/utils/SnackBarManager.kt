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
        message: String?,
        action: (() -> Unit)? = null,
        actionLabel: String? = null,
        duration: SnackbarDuration = SnackbarDuration.Long
    ) {
        _snackBarMessage.send(
            SnackBarManager(
                _message = message,
                _actionLabel = actionLabel,
                _action = action,
                _duration = duration
            )
        )
    }

    suspend fun dismissSnackBar() {
        _snackBarMessage.send(null)
    }

    fun getSnackBarMessage() = _snackBarMessage.receiveAsFlow()
}

@Immutable
class SnackBarManager(
    private val _message: String?,
    private var _action: (() -> Unit)? = null,
    private var _actionLabel: String? = null,
    private var _duration: SnackbarDuration = SnackbarDuration.Long
) {
    fun getMessage() = _message
    fun getActionLabel() = _actionLabel
    fun getDuration() = _duration

    fun performAction() {
        _action?.invoke()
    }
}
