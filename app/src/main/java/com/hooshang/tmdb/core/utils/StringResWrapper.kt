package com.hooshang.tmdb.core.utils

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.StringRes

class StringResWrapper(
    @StringRes val resId: Int
) {
    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var context: Context
        fun setContext(context: Context) {
            this.context = context
        }
    }

    fun asString(): String = context.getString(this.resId)
}