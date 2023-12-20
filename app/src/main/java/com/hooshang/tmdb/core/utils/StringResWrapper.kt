package com.hooshang.tmdb.core.utils

import android.content.Context
import androidx.annotation.StringRes

class StringResWrapper(
    @StringRes val resId : Int
) {
    fun asString(context: Context):String = context.getString(this.resId)
}