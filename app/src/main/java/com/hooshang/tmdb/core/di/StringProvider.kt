package com.hooshang.tmdb.core.di

import android.content.Context
import androidx.annotation.StringRes
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class StringProvider @Inject constructor(@ApplicationContext private val context: Context) {
    fun provideStringResource(@StringRes id: Int): String = context.getString(id)
}