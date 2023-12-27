package com.hooshang.tmdb.core.utils

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

class StringResWrapper(
    @StringRes val resId: Int
) {
    fun asString(context: Context): String = context.getString(this.resId)

    @Composable
    fun asString(): String {
        val context = LocalContext.current
        return context.getString(this.resId)
    }
}