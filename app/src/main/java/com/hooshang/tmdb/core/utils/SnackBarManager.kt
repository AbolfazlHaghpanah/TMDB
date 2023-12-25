package com.hooshang.tmdb.core.utils

import androidx.compose.material.SnackbarDuration
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.annotation.concurrent.Immutable

class SnackBarManager {
    private val _snackBarMessage: Channel<SnackBarMassage?> = Channel()
    val snackBarMessage = _snackBarMessage.receiveAsFlow()
    suspend fun sendMessage(
        snackBarMassage: SnackBarMassage?
    ) {
        _snackBarMessage.send(snackBarMassage)
    }

    suspend fun dismissSnackBar() {
        _snackBarMessage.send(null)
    }
}

@Immutable
data class SnackBarMassage(
    val snackBarMessage: String?,
    val snackBarAction: (() -> Unit)? = null,
    val snackBarActionLabel: StringResWrapper? = null,
    val snackBarDuration: SnackbarDuration = SnackbarDuration.Long,
) {
    fun performAction() {
        snackBarAction?.invoke()
    }
}
